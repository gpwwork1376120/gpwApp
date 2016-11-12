package gpw.com.app.activity;


import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import gpw.com.app.R;
import gpw.com.app.adapter.AddressMainAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.bean.ADInfo;
import gpw.com.app.bean.AddressMainInfo;
import gpw.com.app.util.DensityUtil;
import gpw.com.app.view.ImageCycleView;
import gpw.com.app.view.MainPopupWindow;


public class MainActivity extends BaseActivity implements AddressMainAdapter.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ImageCycleView icv_banner;
    private ImageView iv_below;
    private ImageView iv_confirm_order;
    private ImageView iv_cir_head;
    private View view_line;
    private RecyclerView rv_main_address;
    private ArrayList<AddressMainInfo> mAddressMainInfos;
    private AddressMainAdapter mAddressMainAdapter;
    private LinearLayout ll_car_1;
    private LinearLayout ll_car_2;
    private LinearLayout ll_car_3;
    private LinearLayout ll_car_4;
    private LinearLayout ll_car_5;
    private LinearLayout ll_my_order;
    private Button bt_query;
    private TextView tv_car_1;
    private TextView tv_car_2;
    private TextView tv_car_3;
    private TextView tv_car_4;
    private TextView tv_car_5;
    private int select_car = 1;

    private TextView tv_tel;
    private TextView tv_myOrder;
    private TextView tv_myWallet;
    private TextView tv_myConvoy;
    private TextView tv_common_address;
    private TextView tv_myInvoice;
    private TextView tv_news;
    private TextView tv_news_num;
    private TextView tv_benefit_activity;
    private TextView tv_setting;
    private RelativeLayout rl_head;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void findById() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        view_line = findViewById(R.id.view_line);
        rv_main_address = (RecyclerView) findViewById(R.id.rv_main_address);
        bt_query = (Button) findViewById(R.id.bt_query);

        ll_car_1 = (LinearLayout) findViewById(R.id.ll_car_1);
        ll_car_2 = (LinearLayout) findViewById(R.id.ll_car_2);
        ll_car_3 = (LinearLayout) findViewById(R.id.ll_car_3);
        ll_car_4 = (LinearLayout) findViewById(R.id.ll_car_4);
        ll_car_5 = (LinearLayout) findViewById(R.id.ll_car_5);
        ll_my_order = (LinearLayout) findViewById(R.id.ll_my_order);

        iv_confirm_order = (ImageView) findViewById(R.id.iv_confirm_order);
        iv_cir_head = (ImageView) findViewById(R.id.iv_cir_head);
        icv_banner = (ImageCycleView) findViewById(R.id.icv_banner);
        iv_below = (ImageView) findViewById(R.id.iv_below);

        tv_car_1 = (TextView) findViewById(R.id.tv_car_1);
        tv_car_2 = (TextView) findViewById(R.id.tv_car_2);
        tv_car_3 = (TextView) findViewById(R.id.tv_car_3);
        tv_car_4 = (TextView) findViewById(R.id.tv_car_4);
        tv_car_5 = (TextView) findViewById(R.id.tv_car_5);

        tv_tel = (TextView)findViewById(R.id.tv_tel);
        tv_myOrder = (TextView) findViewById(R.id.tv_myOrder);
        tv_myWallet = (TextView) findViewById(R.id.tv_myWallet);
        tv_myConvoy = (TextView) findViewById(R.id.tv_myConvoy);
        tv_common_address = (TextView) findViewById(R.id.tv_common_address);
        tv_myInvoice = (TextView) findViewById(R.id.tv_myInvoice);
        tv_news = (TextView) findViewById(R.id.tv_news);
        tv_news_num = (TextView) findViewById(R.id.tv_news_num);
        tv_benefit_activity = (TextView) findViewById(R.id.tv_benefit_activity);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(DensityUtil.dip2px(MainActivity.this,10.0f),DensityUtil.dip2px(MainActivity.this,15.0f),0,0);
            iv_cir_head.setLayoutParams(layoutParams);
        }


    }

    @Override
    protected void initData() {
        mAddressMainInfos = new ArrayList<>();
        AddressMainInfo addressMainInfo = new AddressMainInfo();
        addressMainInfo.setAction(0);
        addressMainInfo.setState(1);
        LatLng latLng2 = new LatLng(22.54227041419383, 113.98041663238132);
        addressMainInfo.setLatLng(latLng2);
        addressMainInfo.setStart(true);

        AddressMainInfo addressMainInfo1 = new AddressMainInfo();
        addressMainInfo1.setAction(1);
        addressMainInfo1.setState(3);
        LatLng latLng1 = new LatLng(22.54227041419383, 113.98041663238132);
        addressMainInfo1.setLatLng(latLng1);
        addressMainInfo1.setStart(true);

        AddressMainInfo addressMainInfo2 = new AddressMainInfo();
        addressMainInfo2.setAction(2);
        addressMainInfo2.setAddress("广东省深圳市南山区深南大道9037号");
        addressMainInfo2.setName("世界之窗");
        addressMainInfo2.setContact("龚xx 123456789");
        addressMainInfo2.setState(2);
        LatLng latLng = new LatLng(22.54227041419383, 113.98041663238132);
        addressMainInfo2.setLatLng(latLng);
        addressMainInfo2.setState(2);
        addressMainInfo2.setStart(false);

        mAddressMainInfos.add(addressMainInfo);
        mAddressMainInfos.add(addressMainInfo2);
        mAddressMainInfos.add(addressMainInfo1);

        mAddressMainAdapter = new AddressMainAdapter(mAddressMainInfos, this);

    }

    @Override
    protected void initView() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_main_address.setLayoutManager(layoutManager);
        rv_main_address.setAdapter(mAddressMainAdapter);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.LEFT);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;

                if (drawerView.getTag().equals("LEFT")) {
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
                    mContent.invalidate();

                } else {
                    ViewHelper.setTranslationX(mContent, -mMenu.getMeasuredWidth() * slideOffset);
                    mContent.invalidate();
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                iv_cir_head.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                iv_cir_head.setVisibility(View.VISIBLE);
            }
        });


        ArrayList<ADInfo> adInfos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setUrl("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=tup&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2749032785,4112802673&os=1033870515,2661805642&simid=4214987411,843537031&pn=1&rn=1&di=105759005000&ln=1977&fr=&fmq=1477123249698_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&adpicid=0&pi=0&gsm=0&objurl=http%3A%2F%2F77fkxu.com1.z0.glb.clouddn.com%2F20130427%2F1367044809_48720.jpg&rpstart=0&rpnum=0&adpicid=0");
            adInfo.setId(i + "");
            adInfo.setContent("aaa" + i);
            adInfos.add(adInfo);
        }
        icv_banner.setImageResources(adInfos, new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                imageView.setImageResource(R.mipmap.ic_default);
            }

            @Override
            public void onImageClick(ADInfo info, int position, View imageView) {

            }
        });


        mAddressMainAdapter.setOnItemClickListener(this);
        ll_my_order.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        iv_cir_head.setOnClickListener(this);
        iv_below.setOnClickListener(this);
        ll_car_1.setOnClickListener(this);
        ll_car_2.setOnClickListener(this);
        ll_car_3.setOnClickListener(this);
        ll_car_4.setOnClickListener(this);
        ll_car_5.setOnClickListener(this);
        iv_confirm_order.setOnClickListener(this);
        tv_tel.setOnClickListener(this);
        tv_myOrder.setOnClickListener(this);
        tv_myWallet.setOnClickListener(this);
        tv_myConvoy.setOnClickListener(this);
        tv_common_address.setOnClickListener(this);
        tv_myInvoice.setOnClickListener(this);
        tv_news.setOnClickListener(this);
        tv_benefit_activity.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        rl_head.setOnClickListener(this);


        choiceCar(1);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_car_1:
                choiceCar(1);
                break;
            case R.id.ll_car_2:
                choiceCar(2);
                break;
            case R.id.ll_car_3:
                choiceCar(3);
                break;
            case R.id.ll_car_4:
                choiceCar(4);
                break;
            case R.id.ll_car_5:
                choiceCar(5);
                break;
            case R.id.ll_my_order:
                intent = new Intent(MainActivity.this, MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_query:
//                Intent intent1 = new Intent(MainActivity.this,ConfirmOrderActivity.class);
//                startActivity(intent1);
                break;
            case R.id.iv_cir_head:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_confirm_order:
                intent = new Intent(MainActivity.this, ConfirmOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_below:
                MainPopupWindow selectPicPopupWindow = new MainPopupWindow(MainActivity.this, select_car);
                backgroundAlpha(0.5f);

                //添加pop窗口关闭事件
                selectPicPopupWindow.setOnDismissListener(new popupDismissListener());
                int[] location = new int[2];
                view_line.getLocationOnScreen(location);
                selectPicPopupWindow.showAsDropDown(view_line);
                break;
            case R.id.tv_myOrder:
                intent = new Intent(MainActivity.this, MyOrderActivity.class);
                mDrawerLayout.closeDrawers();
                startActivity(intent);
                break;
            case R.id.tv_myWallet:
                intent = new Intent(MainActivity.this, MyWalletActivity.class);
                mDrawerLayout.closeDrawers();
                startActivity(intent);
                break;
            case R.id.tv_myConvoy:
                intent = new Intent(MainActivity.this, MyConvoyActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_common_address:
                intent = new Intent(MainActivity.this, CommonAddressActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_myInvoice:
                intent = new Intent(MainActivity.this, MyInvoiceActivity.class);
                mDrawerLayout.closeDrawers();
                startActivity(intent);
                break;
            case R.id.tv_news:
                intent = new Intent(MainActivity.this, MyNewsActivity.class);
                mDrawerLayout.closeDrawers();
                startActivity(intent);
                break;
            case R.id.tv_benefit_activity:
                intent = new Intent(MainActivity.this, BenefitActivityActivity.class);
                mDrawerLayout.closeDrawers();
                startActivity(intent);
                break;
            case R.id.tv_setting:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                mDrawerLayout.closeDrawers();
                startActivity(intent);
                break;
            case R.id.rl_head:
                intent = new Intent(MainActivity.this,PersonalInfoActivity.class);
                mDrawerLayout.closeDrawers();
                startActivity(intent);
                break;
        }
    }


    private void initColor() {
        tv_car_1.setTextColor(ContextCompat.getColor(this, R.color.color_gary_font));
        tv_car_2.setTextColor(ContextCompat.getColor(this, R.color.color_gary_font));
        tv_car_3.setTextColor(ContextCompat.getColor(this, R.color.color_gary_font));
        tv_car_4.setTextColor(ContextCompat.getColor(this, R.color.color_gary_font));
        tv_car_5.setTextColor(ContextCompat.getColor(this, R.color.color_gary_font));
    }

    private void choiceCar(int i) {
        initColor();
        switch (i) {
            case 1:
                tv_car_1.setTextColor(ContextCompat.getColor(this, R.color.color_red));
                select_car = 1;
                break;
            case 2:
                tv_car_2.setTextColor(ContextCompat.getColor(this, R.color.color_red));
                select_car = 2;

                break;
            case 3:
                tv_car_3.setTextColor(ContextCompat.getColor(this, R.color.color_red));
                select_car = 3;
                break;
            case 4:
                tv_car_4.setTextColor(ContextCompat.getColor(this, R.color.color_red));
                select_car = 4;
                break;
            case 5:
                tv_car_5.setTextColor(ContextCompat.getColor(this, R.color.color_red));
                select_car = 5;
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("addressMainInfo", mAddressMainInfos.get(position));
        intent.putExtra("type", 3);
        startActivityForResult(intent, 1);

    }

    @Override
    public void onActionClick(int position) {
        mAddressMainInfos.remove(position);
        mAddressMainAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {

            int position = data.getIntExtra("position", 0);
            int type = data.getIntExtra("type", 0);
            AddressMainInfo mAddressMainInfo = data.getParcelableExtra("addressMainInfo");

            System.out.println("type" + type);
            System.out.println("position" + position);
            System.out.println("mAddressMainInfo" + mAddressMainInfo);
            if (type == 2) {
                AddressMainInfo old = mAddressMainInfos.get(position);
                mAddressMainInfos.set(position, mAddressMainInfo);
                mAddressMainInfos.add(old);
                mAddressMainAdapter.notifyDataSetChanged();
            } else {
                mAddressMainInfos.set(position, mAddressMainInfo);
                mAddressMainAdapter.notifyDataSetChanged();
            }

        }
    }

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

}
