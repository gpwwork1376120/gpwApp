package gpw.com.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.adapter.AddressNameAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.CarLoactionInfo;
import gpw.com.app.bean.CommonAdInfo;
import gpw.com.app.util.DensityUtil;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;

public class CarLocationActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private BaiduMap mBaiduMap;
    private LatLng latLng;
    MapView mMapView = null;
    private String TransporterId;


    @Override
    protected int getLayout() {
        return R.layout.activity_car_location;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        mMapView = (MapView) findViewById(R.id.map_view);

    }

    @Override
    protected void initData() {
        TransporterId = getIntent().getStringExtra("TransporterId");
    }

    @Override
    protected void initView() {
        mBaiduMap = mMapView.getMap();
        iv_left_white.setOnClickListener(this);
        tv_title.setText(R.string.car_location);
        tv_right.setVisibility(View.GONE);
        carLocation();

    }

    private void carLocation() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("TransporterId", TransporterId);
        LogUtil.i(jsonObject.toString());
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        HttpUtil.doPost(CarLocationActivity.this, Contants.url_getVehicleLocation, "getVehicleLocation", map, new VolleyInterface(CarLocationActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i(result.toString());
                Gson gson = new Gson();
                CarLoactionInfo carLoactionInfo = gson.fromJson(result,CarLoactionInfo.class);

                mBaiduMap.clear();
                LatLng latLng = new LatLng(carLoactionInfo.getLat(), carLoactionInfo.getLng());
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.location);
                OverlayOptions option = new MarkerOptions()
                        .position(latLng)
                        .icon(bitmap);
                mBaiduMap.addOverlay(option);

                LinearLayout linearLayout = (LinearLayout) View.inflate(CarLocationActivity.this, R.layout.view_map_bck, null);
                TextView tv_map_name = (TextView) linearLayout.findViewById(R.id.tv_map_name);
                TextView tv_map_detail = (TextView) linearLayout.findViewById(R.id.tv_map_detail);
                //tv_map_name.setText("当前位置");
                //tv_map_detail.setText(location.getAddress().address);
                InfoWindow mInfoWindow = new InfoWindow(linearLayout, latLng, -85);
                mBaiduMap.showInfoWindow(mInfoWindow);

                MapStatus mapStatus = new MapStatus.Builder()
                        .target(latLng)
                        .zoom(15.0f)
                        .build();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                        .newMapStatus(mapStatus);
                mBaiduMap.animateMapStatus(mapStatusUpdate);

            }

            @Override
            public void onError(VolleyError error) {
                showShortToastByString(getString(R.string.timeoutError));
//                LogUtil.i("hint",error.networkResponse.headers.toString());
//                LogUtil.i("hint",error.networkResponse.statusCode+"");
            }

            @Override
            public void onStateError() {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
            case R.id.tv_address:
                Intent intent = new Intent(CarLocationActivity.this, CommonAddressActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.removeActivity(this);
        mMapView.onDestroy();
    }


}