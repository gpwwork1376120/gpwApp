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

import java.util.ArrayList;

import gpw.com.app.R;
import gpw.com.app.adapter.AddressNameAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.CommonAdInfo;
import gpw.com.app.bean.OrderAddressInfo;
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
    private SharedPreferences prefs;
    private int pst;
    private int type;
    private String city;
    private String receiptAddress;
    private OrderAddressInfo mOrderAddressInfo;
    private GeoCoder mSearch;
    private String userId;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    MapView mMapView = null;


    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
               // Toast.makeText(MapActivity.this, "没有检索到结果", Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(MapActivity.this, "没有检索到结果", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                // Toast.makeText(MapActivity.this, "没有检索到结果", Toast.LENGTH_SHORT).show();
                return;
            }


            LatLng location = result.getLocation();

            String address = result.getAddress();
            LinearLayout linearLayout = (LinearLayout) View.inflate(MapActivity.this, R.layout.view_map_bck, null);
            TextView tv_map_name = (TextView) linearLayout.findViewById(R.id.tv_map_name);
            TextView tv_map_detail = (TextView) linearLayout.findViewById(R.id.tv_map_detail);
            tv_map_name.setText(receiptAddress);
            tv_map_detail.setText(address);
            InfoWindow mInfoWindow = new InfoWindow(linearLayout, location, -85);
            mBaiduMap.showInfoWindow(mInfoWindow);
            receiptAddress = receiptAddress + "   " + "(" + address + ")";

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
            mBaiduMap.animateMapStatus(mapStatusUpdate);

            lv_search.setVisibility(View.GONE);
            lv_search.setVisibility(View.GONE);


            if (mOrderAddressInfo.getState() == 3 && type == 2) {
                mOrderAddressInfo = new OrderAddressInfo();
                mOrderAddressInfo.setState(2);
                mOrderAddressInfo.setAction(2);
            }

            mOrderAddressInfo.setReceiptAddress(receiptAddress);
            mOrderAddressInfo.setLat(location.latitude);
            mOrderAddressInfo.setLng(location.longitude);
            mOrderAddressInfo.setReceiptAddress(receiptAddress);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapActivity.this, ImproveDisclosureActivity.class);
                    intent.putExtra("position", pst);
                    intent.putExtra("orderAddressInfo", mOrderAddressInfo);
                    intent.putExtra("type", type);
                    startActivityForResult(intent, 4);
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

        prefs = getSharedPreferences(Contants.SHARED_NAME, MODE_PRIVATE);
        city = prefs.getString("city", "深圳市");
        userId = prefs.getString("UserId", "");

        pst = getIntent().getIntExtra("position", 0);
        type = getIntent().getIntExtra("type", 0);
        mOrderAddressInfo = getIntent().getParcelableExtra("orderAddressInfo");

        mLocationClient = new LocationClient(MapActivity.this);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
    }

    private void initLocation() {
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
    protected void initView() {
        mBaiduMap = mMapView.getMap();

        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        lv_search.setAdapter(mAddressNameAdapter);

        if (mOrderAddressInfo.getReceiptAddress().equals("start")) {
            mLocationClient.start();
        } else {
            LatLng latLng = new LatLng(mOrderAddressInfo.getLat(), mOrderAddressInfo.getLng());
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.location);
            OverlayOptions option = new MarkerOptions()
                    .position(latLng)
                    .icon(bitmap);
            mBaiduMap.addOverlay(option);

            LinearLayout linearLayout = (LinearLayout) View.inflate(MapActivity.this, R.layout.view_map_bck, null);
            TextView tv_map_name = (TextView) linearLayout.findViewById(R.id.tv_map_name);
            TextView tv_map_detail = (TextView) linearLayout.findViewById(R.id.tv_map_detail);
            receiptAddress = mOrderAddressInfo.getReceiptAddress();
            String[] nameAd = receiptAddress.split("  ");

            tv_map_name.setText(nameAd[0]);
            tv_map_detail.setText(nameAd[1]);

            InfoWindow mInfoWindow = new InfoWindow(linearLayout, latLng, -85);
            mBaiduMap.showInfoWindow(mInfoWindow);

            MapStatus mapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(15.0f)
                    .build();
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(mapStatus);
            mBaiduMap.animateMapStatus(mapStatusUpdate);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapActivity.this, ImproveDisclosureActivity.class);
                    intent.putExtra("position", pst);
                    intent.putExtra("orderAddressInfo", mOrderAddressInfo);
                    intent.putExtra("type", type);
                    intent.putExtra("userId", getIntent().getStringExtra("userId"));
                    startActivityForResult(intent, 4);
                }
            });
        }


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("sadsad" + s.toString());
                System.out.println("city" + city);
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(s.toString())
                        .city(city));
                if (s.toString().isEmpty()) {
                    mSuggestionInfos.clear();
                    mAddressNameAdapter.notifyDataSetChanged();
                }
            }
        });
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBaiduMap.clear();
                LatLng pt = mSuggestionInfos.get(position).pt;
                receiptAddress = mSuggestionInfos.get(position).key;
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
                intent.putExtra("userId", userId);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 7);
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
        mSuggestionSearch.destroy();
        mLocationClient.unRegisterLocationListener(myListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 4) {
            int position = data.getIntExtra("position", 0);
            int type = data.getIntExtra("type", 0);
            OrderAddressInfo orderAddressInfo = data.getParcelableExtra("orderAddressInfo");
            getIntent().putExtra("position", position);
            getIntent().putExtra("orderAddressInfo", orderAddressInfo);
            getIntent().putExtra("type", type);
            setResult(RESULT_OK, getIntent());
            finish();
        }
        if (resultCode == RESULT_OK && requestCode == 7) {

            CommonAdInfo commonAdInfo = data.getParcelableExtra("commonAdInfo");
            System.out.println(commonAdInfo.toString());
            LatLng latLng = new LatLng(commonAdInfo.getLat(), commonAdInfo.getLng());

            mBaiduMap.clear();
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.location);
            OverlayOptions option = new MarkerOptions()
                    .position(latLng)
                    .icon(bitmap);
            mBaiduMap.addOverlay(option);


            MapStatus mapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(15.0f)
                    .build();
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(mapStatus);
            mBaiduMap.animateMapStatus(mapStatusUpdate);

            lv_search.setVisibility(View.GONE);
            lv_search.setVisibility(View.GONE);

            LinearLayout linearLayout = (LinearLayout) View.inflate(MapActivity.this, R.layout.view_map_bck, null);
            TextView tv_map_name = (TextView) linearLayout.findViewById(R.id.tv_map_name);
            TextView tv_map_detail = (TextView) linearLayout.findViewById(R.id.tv_map_detail);
            receiptAddress = commonAdInfo.getReceiptAddress();
            String[] nameAd = receiptAddress.split("  ");
            tv_map_name.setText(nameAd[0]);
            tv_map_detail.setText(nameAd[1]);
            InfoWindow mInfoWindow = new InfoWindow(linearLayout, latLng, -85);
            mBaiduMap.showInfoWindow(mInfoWindow);

            if (mOrderAddressInfo.getState() == 3 && type == 2) {
                mOrderAddressInfo = new OrderAddressInfo();
                mOrderAddressInfo.setState(2);
                mOrderAddressInfo.setAction(2);
            }

            mOrderAddressInfo.setLat(latLng.latitude);
            mOrderAddressInfo.setLng(latLng.longitude);
            mOrderAddressInfo.setReceiptAddress(receiptAddress);
            mOrderAddressInfo.setReceipter(commonAdInfo.getReceipter());
            mOrderAddressInfo.setReceiptTel(commonAdInfo.getReceiptTel());
            System.out.println(mOrderAddressInfo.toString());
            Intent intent = new Intent(MapActivity.this, ImproveDisclosureActivity.class);
            intent.putExtra("position", pst);
            intent.putExtra("orderAddressInfo", mOrderAddressInfo);
            intent.putExtra("type", type);
            startActivityForResult(intent, 4);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapActivity.this, ImproveDisclosureActivity.class);
                    intent.putExtra("position", pst);
                    intent.putExtra("orderAddressInfo", mOrderAddressInfo);
                    intent.putExtra("type", type);
                    startActivityForResult(intent, 4);
                }
            });
        }

    }


    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            city = location.getCity();
            if (city == null) {
                mLocationClient.stop();
                mLocationClient.start();
                return;
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("city", city);
            editor.apply();


            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.location);
            OverlayOptions option = new MarkerOptions()
                    .position(latLng)
                    .icon(bitmap);
            mBaiduMap.addOverlay(option);


            LinearLayout linearLayout = (LinearLayout) View.inflate(MapActivity.this, R.layout.view_map_bck, null);
            TextView tv_map_name = (TextView) linearLayout.findViewById(R.id.tv_map_name);
            TextView tv_map_detail = (TextView) linearLayout.findViewById(R.id.tv_map_detail);
            tv_map_name.setText("当前位置");
            tv_map_detail.setText(location.getAddress().address);
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
    }

}
