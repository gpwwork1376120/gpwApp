package gpw.com.app.util;

import android.content.Context;
import android.widget.Toast;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import gpw.com.app.R;
import gpw.com.app.base.BaseApplication;




public class BdMapLocationUtil {

    private static BdMapLocationUtil single = null;

    // 定位客户端  
    private LocationClient mLocationClient = null;

    // 百度定位结果回调  
    private BDLocationListener myListener = new MyLocationListener();

    // 定位成功返回信息的回调  
    private BdLocationSuccessListenner listenner = null;

    private String city;
    private static Context mContext;

    public interface BdLocationSuccessListenner {
        void locationResult(double _latitude, double _longitude, String _locationAddr, String _locationCity);
    }

    private BdMapLocationUtil() {
        if (mLocationClient == null)
            initClient();


    }

    // 静态工厂方法
    public static BdMapLocationUtil getInstance(Context context) {
        if (single == null) {
            single = new BdMapLocationUtil();
        }
        mContext =context;
        return single;
    }

    private void initClient() {
        // 声明LocationClient类
        mLocationClient = new LocationClient(mContext);
        initLoctaionOpt();
    }


    // 初始化定位参数  
    private void initLoctaionOpt() {

        LocationClientOption mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setOpenGps(true);
        mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mLocationClient.setLocOption(mOption);
        // 注册监听函数  
        mLocationClient.registerLocationListener(myListener);
    }

    /**
     * 启动百度定位
     *
     * @param
     */

    public void startLocation(BdLocationSuccessListenner listenner) {

        Toast.makeText(mContext,R.string.Neterror, Toast.LENGTH_SHORT).show();
        if (!NetworkUtil.isConnected(mContext)) {
            Toast.makeText(mContext,R.string.Neterror, Toast.LENGTH_SHORT).show();
            return;
        }


        this.listenner = listenner;

        if (mLocationClient == null)
            initClient();

        if (!mLocationClient.isStarted())
            mLocationClient.start();

        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        }
    }

    public void stopLocation() {
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            city = location.getCity();
            if (city == null) {
                mLocationClient.stop();
                mLocationClient.start();
                return;
            }
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String address = location.getAddress().address;

            if (listenner != null) {
                listenner.locationResult(latitude,longitude,city,address);
            }
        }
    }


}  