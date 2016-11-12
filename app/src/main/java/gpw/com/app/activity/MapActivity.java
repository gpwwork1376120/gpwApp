package gpw.com.app.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

import gpw.com.app.R;
import gpw.com.app.adapter.AddressNameAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.bean.AddressMainInfo;
import gpw.com.app.util.DensityUtil;

public class MapActivity extends BaseActivity {

    private ImageView iv_left_black;
    private EditText et_search;
    private TextView tv_address;
    private ListView lv_search;
    private LinearLayout ll_search;
    private SuggestionSearch mSuggestionSearch;
    private AddressNameAdapter mAddressNameAdapter;
    private ArrayList<SuggestionResult.SuggestionInfo> mSuggestionInfos;
    private BaiduMap mBaiduMap;
    private int pst;
    private int type;
    private AddressMainInfo mAddressMainInfo;
    private GeoCoder mSearch;
    MapView mMapView = null;


    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                Toast.makeText(MapActivity.this, "没有检索到结果", Toast.LENGTH_SHORT).show();
                return;
            }
            lv_search.setVisibility(View.VISIBLE);
            System.out.println(res.getAllSuggestions().toString());
            mSuggestionInfos.clear();
            mSuggestionInfos.addAll(res.getAllSuggestions());
            mAddressNameAdapter.notifyDataSetChanged();
        }
    };
    OnGetGeoCoderResultListener listener1 = new OnGetGeoCoderResultListener() {
        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(MapActivity.this, "没有检索到结果", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(MapActivity.this, "没有检索到结果", Toast.LENGTH_SHORT).show();
                return;
            }


            LatLng location = result.getLocation();
            String name = mAddressMainInfo.getName();
            String address = result.getAddress();
            Button button = new Button(getApplicationContext());
            button.setText(name + "\n" + address);
            InfoWindow mInfoWindow = new InfoWindow(button, location, -80);
            mBaiduMap.showInfoWindow(mInfoWindow);


            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.location);
            OverlayOptions option = new MarkerOptions()
                    .position(location)
                    .icon(bitmap);
            mBaiduMap.addOverlay(option);


            MapStatus mapStatus = new MapStatus.Builder()
                    .target(location)
                    .zoom(15.0f)
                    .build();
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(mapStatus);
            mBaiduMap.setMapStatus(mapStatusUpdate);

            lv_search.setVisibility(View.GONE);


            if (mAddressMainInfo.isStart()) {
                mAddressMainInfo.setStart(false);
            }
            if (mAddressMainInfo.getState() == 3 && type == 2) {
                mAddressMainInfo = new AddressMainInfo();
                mAddressMainInfo.setStart(false);
                mAddressMainInfo.setState(2);
                mAddressMainInfo.setAction(2);
            }
            mAddressMainInfo.setAddress(address);
            mAddressMainInfo.setLatLng(location);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapActivity.this, ImproveDisclosureActivity.class);
                    intent.putExtra("position", pst);
                    intent.putExtra("addressMainInfo", mAddressMainInfo);
                    intent.putExtra("type", type);
                    startActivityForResult(intent, 2);
                }
            });
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void findById() {
        mMapView = (MapView) findViewById(R.id.map_view);
        iv_left_black = (ImageView) findViewById(R.id.iv_left_black);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_address = (TextView) findViewById(R.id.tv_address);
        lv_search = (ListView) findViewById(R.id.lv_search);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(MapActivity.this, 50.0f));
            layoutParams.setMargins(DensityUtil.dip2px(MapActivity.this, 5.0f), DensityUtil.dip2px(MapActivity.this, 15.0f), DensityUtil.dip2px(MapActivity.this, 5.0f), 0);
            ll_search.setLayoutParams(layoutParams);
        }


    }

    @Override
    protected void initData() {
        mSuggestionInfos = new ArrayList<>();
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSearch = GeoCoder.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        mSearch.setOnGetGeoCodeResultListener(listener1);
        mAddressNameAdapter = new AddressNameAdapter(mSuggestionInfos, this);
        pst = getIntent().getIntExtra("position", 0);
        type = getIntent().getIntExtra("type", 0);
        mAddressMainInfo = getIntent().getParcelableExtra("addressMainInfo");
        System.out.println(mAddressMainInfo.getLatLng().toString());
    }

    @Override
    protected void initView() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        lv_search.setAdapter(mAddressNameAdapter);

        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(mAddressMainInfo.getLatLng()));

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("sadsad11" + s.toString());
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(s.toString())
                        .city("深圳"));
            }
        });
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBaiduMap.clear();
                LatLng pt = mSuggestionInfos.get(position).pt;
                String name = mSuggestionInfos.get(position).key;
                mAddressMainInfo.setName(name);
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(pt));


            }
        });
        iv_left_black.setOnClickListener(this);
        tv_address.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_black:
                finish();
                break;
            case R.id.tv_address:
                Intent intent = new Intent(MapActivity.this, CommonAddressActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.removeActivity(this);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mSuggestionSearch.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2) {
            int position = data.getIntExtra("position", 0);
            int type = data.getIntExtra("type", 0);
            AddressMainInfo addressMainInfo = data.getParcelableExtra("addressMainInfo");
            getIntent().putExtra("position", position);
            getIntent().putExtra("addressMainInfo", addressMainInfo);
            getIntent().putExtra("type", type);
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }
}
