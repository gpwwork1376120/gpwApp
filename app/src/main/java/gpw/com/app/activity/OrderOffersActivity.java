package gpw.com.app.activity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.adapter.OrderOffersAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.OrderOfferInfo;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;

public class OrderOffersActivity extends BaseActivity {
    private TextView tv_title;
    private TextView tv_right;
    private TextView tv_empty;
    private ImageView iv_left_white;
    private ListView lv_order_offers;
    private ArrayList<OrderOfferInfo> orderOfferInfos;
    private OrderOffersAdapter orderOffersAdapter;
    private String orderId;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected int getLayout() {
        return R.layout.activity_order_offers;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        lv_order_offers = (ListView) findViewById(R.id.lv_order_offers);
        tv_empty = (TextView) findViewById(R.id.tv_empty);


    }

    private void initLocation() {
        mLocationClient = new LocationClient(OrderOffersActivity.this);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        LocationClientOption mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setOpenGps(true);
        mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mLocationClient.setLocOption(mOption);
    }

    @Override
    protected void initData() {
        orderOfferInfos = new ArrayList<>();
        orderOffersAdapter = new OrderOffersAdapter(orderOfferInfos, this);
        orderId = getIntent().getStringExtra("orderId");
        initLocation();
    }


    @Override
    protected void initView() {
        tv_title.setText(R.string.order_offers);
        lv_order_offers.setAdapter(orderOffersAdapter);
        lv_order_offers.setEmptyView(tv_empty);
        tv_right.setVisibility(View.GONE);
        iv_left_white.setOnClickListener(this);
        mLocationClient.start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.removeActivity(this);
        mLocationClient.unRegisterLocationListener(myListener);
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String city = location.getCity();
            LogUtil.i(city);
            if (city == null) {
                mLocationClient.stop();
                mLocationClient.start();
                return;
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserId", Contants.userId);
            jsonObject.addProperty("OrderNo", orderId);
            jsonObject.addProperty("Lat", location.getLatitude());
            jsonObject.addProperty("Lng", location.getLongitude());
            Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
            HttpUtil.doPost(OrderOffersActivity.this, Contants.url_getOrderOffers, "getUserBalance", map, new VolleyInterface(OrderOffersActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                @Override
                public void onSuccess(JsonElement result) {
                    LogUtil.i("offer"+result.toString());
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<OrderOfferInfo>>() {
                    }.getType();
                    ArrayList<OrderOfferInfo> newOrderOfferInfos = gson.fromJson(result, listType);
                    orderOfferInfos.clear();
                    orderOfferInfos.addAll(newOrderOfferInfos);
                    orderOffersAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(VolleyError error) {
                    showShortToastByString(getString(R.string.timeoutError));
//                        LogUtil.i("hint",error.networkResponse.headers.toString());
//                        LogUtil.i("hint",error.networkResponse.statusCode+"");
                }

                @Override
                public void onStateError() {
                }
            });

        }
    }
}
