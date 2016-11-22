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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nineoldandroids.view.ViewHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.adapter.OrderAddressAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.ADInfo;
import gpw.com.app.bean.CarInfo;
import gpw.com.app.bean.FreightInfo;
import gpw.com.app.bean.OrderAddressInfo;
import gpw.com.app.bean.UserInfo;
import gpw.com.app.util.DateUtil;
import gpw.com.app.util.DensityUtil;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;
import gpw.com.app.view.CircleImageView;
import gpw.com.app.view.CustomDatePicker;
import gpw.com.app.view.ImageCycleView;


public class MainActivity extends BaseActivity implements OrderAddressAdapter.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ImageCycleView icv_banner;
    private CircleImageView civ_head;
    private ImageView iv_below;
    private ImageView iv_confirm_order;
    private ImageView iv_cir_head;

    private RecyclerView rv_main_address;
    private OrderAddressAdapter mOrderAddressAdapter;
    private Button bt_query;

    private UserInfo userInfo;
    private ArrayList<ADInfo> adInfos;
    private ArrayList<CarInfo> carInfos;
    private ArrayList<TextView> tvs_car;
    private ArrayList<OrderAddressInfo> mOrderAddressInfos;

    private TextView tv_car_1;
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
    private TextView tv_volume;
    private TextView tv_start_money;
    private TextView tv_kg;
    private TextView tv_after;
    private TextView tv_money;
    private TextView tv_money_detail;
    private TextView tv_goods;

    private RelativeLayout rl_head;
    private RelativeLayout rl_car;
    private RelativeLayout rl_volume;
    private RelativeLayout rl_kg;
    private RelativeLayout rl_amount;
    private RelativeLayout rl_insurance;

    private EditText et_volume;
    private EditText et_kg;
    private EditText et_amount;
    private EditText et_toPayFreightTel;
    private EditText et_remark;

    private LinearLayout ll_total_car;
    private LinearLayout ll_booking_delivery;
    private LinearLayout dialog_car;
    private LinearLayout ll_car_1;
    private LinearLayout ll_my_order;
    private LinearLayout ll_0;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private ImageView iv_above_gray;
    private TextView tv_remark;

    private String orderAddress;
    private int vehideTypeId;
    private double payment;
    private boolean isPublish;
    private String startLngLat;
    private String endLngLat;
    private double premiums;
    private double freight;
    private String volume;
    private String kg;
    private String quantity;

    private boolean isRemove;
    private boolean isMove;
    private boolean isToPayFreight;
    private boolean isCollectionPayment;
    private boolean isMyFleet;
    private boolean isSurcharge;

    private CheckBox cb_isRemove;
    private CheckBox cb_isMove;
    private CheckBox cb_isToPayFreight;
    private CheckBox cb_isSurcharge;
    private CheckBox cb_isCollectionPayment;
    private CheckBox cb_isMyFleet;
    private CheckBox cb_insurance;
    private Button bt_ok;
    private int cofirmTypeId;
    private String payFreightTel;
    private String remark;
    private String mapJson;
    private CustomDatePicker timePickerView;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void findById() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        rv_main_address = (RecyclerView) findViewById(R.id.rv_main_address);
        bt_query = (Button) findViewById(R.id.bt_query);

        ll_car_1 = (LinearLayout) findViewById(R.id.ll_car_1);
        ll_total_car = (LinearLayout) findViewById(R.id.ll_total_car);
        ll_my_order = (LinearLayout) findViewById(R.id.ll_my_order);
        ll_booking_delivery = (LinearLayout) findViewById(R.id.ll_booking_delivery);
        rl_car = (RelativeLayout) findViewById(R.id.rl_car);
        rl_insurance = (RelativeLayout) findViewById(R.id.rl_insurance);

        cb_insurance = (CheckBox) findViewById(R.id.cb_insurance);

        iv_confirm_order = (ImageView) findViewById(R.id.iv_confirm_order);
        iv_cir_head = (ImageView) findViewById(R.id.iv_cir_head);
        icv_banner = (ImageCycleView) findViewById(R.id.icv_banner);
        iv_below = (ImageView) findViewById(R.id.iv_below);

        tv_car_1 = (TextView) findViewById(R.id.tv_car_1);


        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_myOrder = (TextView) findViewById(R.id.tv_myOrder);
        tv_myWallet = (TextView) findViewById(R.id.tv_myWallet);
        tv_myConvoy = (TextView) findViewById(R.id.tv_myConvoy);
        tv_common_address = (TextView) findViewById(R.id.tv_common_address);
        tv_myInvoice = (TextView) findViewById(R.id.tv_myInvoice);
        tv_news = (TextView) findViewById(R.id.tv_news);
        tv_news_num = (TextView) findViewById(R.id.tv_news_num);
        tv_benefit_activity = (TextView) findViewById(R.id.tv_benefit_activity);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_money_detail = (TextView) findViewById(R.id.tv_money_detail);
        tv_goods = (TextView) findViewById(R.id.tv_goods);

        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        civ_head = (CircleImageView) findViewById(R.id.civ_head);


        dialog_car = (LinearLayout) findViewById(R.id.dialog_car);

        et_volume = (EditText) dialog_car.findViewById(R.id.et_volume);
        et_kg = (EditText) dialog_car.findViewById(R.id.et_kg);
        et_amount = (EditText) dialog_car.findViewById(R.id.et_amount);
        et_toPayFreightTel = (EditText) dialog_car.findViewById(R.id.et_toPayFreightTel);
        et_remark = (EditText) dialog_car.findViewById(R.id.et_remark);

        tv_volume = (TextView) dialog_car.findViewById(R.id.tv_volume);
        tv_start_money = (TextView) dialog_car.findViewById(R.id.tv_start_money);
        tv_kg = (TextView) dialog_car.findViewById(R.id.tv_kg);
        tv_after = (TextView) dialog_car.findViewById(R.id.tv_after);
        tv_remark = (TextView) dialog_car.findViewById(R.id.tv_remark);
        iv_above_gray = (ImageView) dialog_car.findViewById(R.id.iv_above_gray);

        rl_volume = (RelativeLayout) dialog_car.findViewById(R.id.rl_volume);
        rl_kg = (RelativeLayout) dialog_car.findViewById(R.id.rl_kg);
        rl_amount = (RelativeLayout) dialog_car.findViewById(R.id.rl_amount);
        ll_0 = (LinearLayout) dialog_car.findViewById(R.id.ll_0);
        ll_1 = (LinearLayout) dialog_car.findViewById(R.id.ll_1);
        ll_2 = (LinearLayout) dialog_car.findViewById(R.id.ll_2);

        cb_isRemove = (CheckBox) dialog_car.findViewById(R.id.cb_isRemove);
        cb_isMove = (CheckBox) dialog_car.findViewById(R.id.cb_isMove);
        cb_isToPayFreight = (CheckBox) dialog_car.findViewById(R.id.cb_isToPayFreight);
        cb_isCollectionPayment = (CheckBox) dialog_car.findViewById(R.id.cb_isCollectionPayment);
        cb_isMyFleet = (CheckBox) dialog_car.findViewById(R.id.cb_isMyFleet);
        cb_isSurcharge = (CheckBox) dialog_car.findViewById(R.id.cb_isSurcharge);
        bt_ok = (Button) dialog_car.findViewById(R.id.bt_ok);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(DensityUtil.dip2px(MainActivity.this, 10.0f), DensityUtil.dip2px(MainActivity.this, 15.0f), 0, 0);
            iv_cir_head.setLayoutParams(layoutParams);
        }


    }

    @Override
    protected void initData() {
        userInfo = getIntent().getParcelableExtra("userInfo");
        adInfos = getIntent().getParcelableArrayListExtra("adInfos");
        tvs_car = new ArrayList<>();
        premiums = 20;
        cofirmTypeId = -1;

        mOrderAddressInfos = new ArrayList<>();
        OrderAddressInfo orderAddressInfo = new OrderAddressInfo();
        orderAddressInfo.setAction(0);
        orderAddressInfo.setState(1);
        orderAddressInfo.setReceiptAddress("start");

        OrderAddressInfo orderAddressInfo1 = new OrderAddressInfo();
        orderAddressInfo1.setAction(1);
        orderAddressInfo1.setState(3);
        orderAddressInfo1.setReceiptAddress("start");
        mOrderAddressInfos.add(orderAddressInfo);
        mOrderAddressInfos.add(orderAddressInfo1);

        mOrderAddressAdapter = new OrderAddressAdapter(mOrderAddressInfos, this);


    }


    @Override
    protected void initView() {

        tv_tel.setText(userInfo.getUserName());
        HttpUtil.setImageLoader(Contants.imagehost + userInfo.getHeadIco(), civ_head, R.mipmap.account, R.mipmap.account);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_main_address.setLayoutManager(layoutManager);
        rv_main_address.setAdapter(mOrderAddressAdapter);


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


        mOrderAddressAdapter.setOnItemClickListener(this);
        ll_my_order.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        iv_cir_head.setOnClickListener(this);
        iv_below.setOnClickListener(this);
        ll_car_1.setOnClickListener(this);
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
        iv_above_gray.setOnClickListener(this);
        rl_volume.setOnClickListener(this);
        rl_kg.setOnClickListener(this);
        rl_amount.setOnClickListener(this);
        rl_insurance.setOnClickListener(this);
        tv_money_detail.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
        ll_booking_delivery.setOnClickListener(this);
        cb_insurance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rl_insurance.setVisibility(View.VISIBLE);
                    premiums = 20;
                    if (isPublish) {
                        double money = freight + premiums;
                        tv_money.setText(String.format("¥%s", money));
                    }

                } else {

                    rl_insurance.setVisibility(View.GONE);
                    premiums = 0;
                    if (isPublish) {
                        double money = freight + premiums;
                        tv_money.setText(String.format("¥%s", money));
                    }
                }
            }
        });

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("AdvertisingType", 1);
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        HttpUtil.doPost(MainActivity.this, Contants.url_getAdvertisings, "getAdvertisings", map, new VolleyInterface(MainActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i("picture" + result.toString());
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ADInfo>>() {
                }.getType();
                adInfos = gson.fromJson(result, listType);
                icv_banner.setImageResources(adInfos, new ImageCycleView.ImageCycleViewListener() {
                    @Override
                    public void displayImage(String imageURL, ImageView imageView) {
                        imageURL = Contants.imagehost + imageURL;
                        HttpUtil.setImageLoader(imageURL, imageView, R.mipmap.ic_default, R.mipmap.ic_default);
                    }

                    @Override
                    public void onImageClick(ADInfo info, int position, View imageView) {

                    }
                });
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
        jsonObject = new JsonObject();
        map = EncryptUtil.encryptDES(jsonObject.toString());
        HttpUtil.doPost(MainActivity.this, Contants.url_getVehicleTypes, "getVehicleTypes", map, new VolleyInterface(MainActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i(result.toString());
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CarInfo>>() {
                }.getType();
                carInfos = gson.fromJson(result, listType);
                int size = carInfos.size();
                for (int i = 0; i < size; i++) {
                    LinearLayout.LayoutParams car_params = new LinearLayout.LayoutParams(
                            0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    LinearLayout.LayoutParams view_params = new LinearLayout.LayoutParams(
                            DensityUtil.dip2px(MainActivity.this, 0.5f), LinearLayout.LayoutParams.MATCH_PARENT);
                    view_params.setMargins(0, DensityUtil.dip2px(MainActivity.this, 5.0f), 0, DensityUtil.dip2px(MainActivity.this, 5.0f));
                    final CarInfo carInfo = carInfos.get(i);
                    LinearLayout linearLayout = (LinearLayout) View.inflate(MainActivity.this, R.layout.view_ll_car, null);
                    ImageView iv_car = (ImageView) linearLayout.findViewById(R.id.iv_car);
                    TextView tv_car = (TextView) linearLayout.findViewById(R.id.tv_car);
                    tv_car.setText(carInfo.getVehicleTypeName());
                    tvs_car.add(tv_car);
                    String imgUrl = Contants.imagehost + carInfo.getImg();
                    LogUtil.i(imgUrl);
                    HttpUtil.setImageLoader(imgUrl, iv_car, 0, 0);
                    View view = View.inflate(MainActivity.this, R.layout.view_line, null);
                    ll_total_car.addView(view, view_params);
                    ll_total_car.addView(linearLayout, car_params);

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int type = Integer.valueOf(carInfo.getTypeCode());
                            choiceCar(type - 1);
                        }
                    });

                }
                choiceCar(4);
            }

            @Override
            public void onError(VolleyError error) {
                showShortToastByString(getString(R.string.timeoutError));
            }

            @Override
            public void onStateError() {
            }
        });

        timePickerView = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                LogUtil.i(time);
                if (cofirmTypeId == 1) {
                    publishCarpool(2, time);
                } else {
                    sendOrder(2, time);
                }
            }
        }, "2010-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行

        timePickerView.showSpecificTime(true); // 显示时和分
        timePickerView.setIsLoop(true); // 允许循环滚动
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_car_1:
                choiceCar(4);
                break;
            case R.id.ll_my_order:
                intent = new Intent(MainActivity.this, MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_query:
                if (vehideTypeId == 1) {
                    publishCarpool(3, DateUtil.getCurrentDates());
                } else {
                    sendOrder(3, DateUtil.getCurrentDates());
                }
                break;
            case R.id.rl_insurance:
                intent = new Intent(MainActivity.this, CargoInsuranceActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.iv_cir_head:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.bt_ok:
                if (vehideTypeId == 0) {
                    volume = et_volume.getText().toString().trim();
                    kg = et_kg.getText().toString().trim();
                    quantity = et_amount.getText().toString().trim();

                    if (volume.isEmpty()) {
                        showShortToastByString("货物的体积不能为空");
                        return;
                    }
                    if (kg.isEmpty()) {
                        showShortToastByString("货物的重量不能为空");
                        return;
                    }
                    cb_isRemove.setChecked(false);

                }
                payFreightTel = et_toPayFreightTel.getText().toString();
                isRemove = cb_isRemove.isChecked();
                isMove = cb_isMove.isChecked();
                isToPayFreight = cb_isToPayFreight.isChecked();
                isCollectionPayment = cb_isCollectionPayment.isChecked();
                isMyFleet = cb_isMyFleet.isChecked();
                isSurcharge = cb_isSurcharge.isChecked();
                remark = et_remark.getText().toString();

                if (isToPayFreight && payFreightTel.isEmpty()) {
                    showShortToastByString("号码不能为空");
                    return;
                }
                cofirmTypeId = vehideTypeId;
                int white = 0x00000000;
                rl_car.setBackgroundColor(white);
                rl_car.setClickable(false);
                dialog_car.setVisibility(View.GONE);
                calculateFreight();
                break;
            case R.id.tv_money_detail:
                intent = new Intent(MainActivity.this, MoneyDetailActivity.class);
                intent.putExtra("freight", freight);
                intent.putExtra("premiums", premiums);
                startActivity(intent);
                break;
            case R.id.iv_confirm_order:
                if (cofirmTypeId == 1) {
                    publishCarpool(1, DateUtil.getCurrentDates());
                } else {
                    sendOrder(1, DateUtil.getCurrentDates());
                }
                break;
            case R.id.ll_booking_delivery:
                timePickerView.show(DateUtil.getCurrentDates());
                break;
            case R.id.iv_below:
                int gray = 0x77000000;
                rl_car.setBackgroundColor(gray);
                rl_car.setClickable(true);
                dialog_car.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_above_gray:
                int white1 = 0x00000000;
                rl_car.setBackgroundColor(white1);
                rl_car.setClickable(false);
                dialog_car.setVisibility(View.GONE);
                break;
            case R.id.tv_myOrder:
                intent = new Intent(MainActivity.this, MyOrderActivity.class);
                intent.putExtra("UserId", userInfo.getUserId());
                startActivity(intent);
                break;
            case R.id.tv_myWallet:
                intent = new Intent(MainActivity.this, MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_myConvoy:
                intent = new Intent(MainActivity.this, MyConvoyActivity.class);
                intent.putExtra("UserId", userInfo.getUserId());
                startActivity(intent);
                break;
            case R.id.tv_common_address:
                intent = new Intent(MainActivity.this, CommonAddressActivity.class);
                intent.putExtra("userId", userInfo.getUserId());
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.tv_myInvoice:
                intent = new Intent(MainActivity.this, MyInvoiceActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_news:
                intent = new Intent(MainActivity.this, MyNewsActivity.class);

                startActivity(intent);
                break;
            case R.id.tv_benefit_activity:
                intent = new Intent(MainActivity.this, BenefitActivityActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_setting:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.putExtra("userInfo", userInfo);
                startActivityForResult(intent, 3);
                break;
            case R.id.rl_head:
                intent = new Intent(MainActivity.this, PersonalInfoActivity.class);
                intent.putExtra("userInfo", userInfo);
                startActivityForResult(intent, 3);
                break;
        }
    }


    private void initColor() {
        tv_car_1.setTextColor(ContextCompat.getColor(this, R.color.color_gary_font));
        int size = tvs_car.size();
        for (int i = 0; i < size; i++) {
            tvs_car.get(i).setTextColor(ContextCompat.getColor(this, R.color.color_gary_font));
        }
    }

    private void choiceCar(int i) {
        initColor();
        CarInfo carInfo = null;
        if (i != 4) {
            carInfo = carInfos.get(i);
        }
        switch (i) {
            case 4:
                tv_car_1.setTextColor(ContextCompat.getColor(this, R.color.color_red));
                Visible1();
                vehideTypeId = 0;
                break;
            case 0:
                tvs_car.get(0).setTextColor(ContextCompat.getColor(this, R.color.color_red));
                Visible(carInfo);
                vehideTypeId = 1;
                cb_isRemove.setText("全拆座");
                break;
            case 1:
                tvs_car.get(1).setTextColor(ContextCompat.getColor(this, R.color.color_red));
                Visible(carInfo);
                vehideTypeId = 2;
                cb_isRemove.setText("全拆座");
                break;
            case 2:
                tvs_car.get(2).setTextColor(ContextCompat.getColor(this, R.color.color_red));
                Visible(carInfo);
                vehideTypeId = 3;
                cb_isRemove.setText("开顶");
                break;
            case 3:
                tvs_car.get(3).setTextColor(ContextCompat.getColor(this, R.color.color_red));
                Visible(carInfo);
                vehideTypeId = 4;
                cb_isRemove.setText("开顶");
                break;
        }

    }

    private void Visible(CarInfo carInfo) {
        ll_0.setVisibility(View.GONE);
        ll_1.setVisibility(View.VISIBLE);
        ll_2.setVisibility(View.VISIBLE);
        cb_isRemove.setVisibility(View.VISIBLE);

        if (vehideTypeId == 0) {
            cb_isRemove.setChecked(false);
            cb_isMove.setChecked(false);
            cb_isToPayFreight.setChecked(false);
            cb_isCollectionPayment.setChecked(false);
            cb_isMyFleet.setChecked(false);
            cb_isSurcharge.setChecked(false);
            et_remark.setText("");
            et_toPayFreightTel.setText("");
        } else {
            cb_isRemove.setChecked(isRemove);
            cb_isMove.setChecked(isMove);
            cb_isToPayFreight.setChecked(isToPayFreight);
            cb_isCollectionPayment.setChecked(isCollectionPayment);
            cb_isMyFleet.setChecked(isMyFleet);
            cb_isSurcharge.setChecked(isSurcharge);
            et_remark.setText(remark);
            et_toPayFreightTel.setText(payFreightTel);
        }

        tv_volume.setText(String.format("运输体积:%sm³", carInfo.getVolume()));
        tv_kg.setText(String.format("载重:%skg", carInfo.getLoadWeight()));
        tv_after.setText(String.format("后续:%s元/Km", carInfo.getFollowPrice()));
        tv_start_money.setText(String.format("起步价:%s元/5Km", carInfo.getStartingPrice()));
        tv_remark.setText(String.format("注:%s", carInfo.getRemark()));
    }

    private void Visible1() {
        ll_0.setVisibility(View.VISIBLE);
        ll_1.setVisibility(View.GONE);
        ll_2.setVisibility(View.GONE);
        cb_isRemove.setVisibility(View.GONE);
        if (vehideTypeId != 0) {
            cb_isRemove.setChecked(false);
            cb_isMove.setChecked(false);
            cb_isToPayFreight.setChecked(false);
            cb_isCollectionPayment.setChecked(false);
            cb_isMyFleet.setChecked(false);
            cb_isSurcharge.setChecked(false);
            et_remark.setText("");
            et_toPayFreightTel.setText("");
        }
    }


    private void sendOrder(int type, String time) {

        if (!isPublish) {
            showShortToastByString("地址信息不完整");
            return;
        }
        if (cofirmTypeId == -1) {
            showShortToastByString("未确定规格");
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SendUserId", userInfo.getUserId());
        jsonObject.addProperty("VehideTypeId", cofirmTypeId);
        jsonObject.addProperty("IsRemove", isRemove);
        jsonObject.addProperty("IsMove", isMove);
        jsonObject.addProperty("IsSurcharge", isSurcharge);
        jsonObject.addProperty("IsToPayFreight", isToPayFreight);
        jsonObject.addProperty("ToPayFreightTel", payFreightTel);
        jsonObject.addProperty("IsCollectionPayment", isCollectionPayment);
        jsonObject.addProperty("Payment", payment);
        jsonObject.addProperty("IsMyFleet", isMyFleet);
        jsonObject.addProperty("OrderAddress", orderAddress);
        jsonObject.addProperty("Premiums", premiums);
        jsonObject.addProperty("Remark", remark);
        jsonObject.addProperty("OrderType", type);
        jsonObject.addProperty("PlanSendTime", time);
        LogUtil.i(jsonObject.toString());
        mapJson = jsonObject.toString();
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        if (type == 3) {
            HttpUtil.doPost(MainActivity.this, Contants.url_sendOrder, "sendOrder", map, new VolleyInterface(MainActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                @Override
                public void onSuccess(JsonElement result) {
                    LogUtil.i(result.toString());
                    showShortToastByString("货源发布成功");
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
        } else {
            Intent intent = new Intent(MainActivity.this, ConfirmOrderActivity.class);
            String money = tv_money.getText().toString();
            intent.putExtra("type", type);
            intent.putExtra("money", money);
            intent.putExtra("mapJson", mapJson);
            intent.putExtra("time", time);
            intent.putExtra("carType", 1);
            intent.putParcelableArrayListExtra("OrderAddressInfos", mOrderAddressInfos);
            startActivity(intent);
        }
    }

    private void publishCarpool(int type, String time) {
        if (!isPublish) {
            showShortToastByString("地址信息不完整");
            return;
        }
        if (cofirmTypeId == -1) {
            showShortToastByString("未确定规格");
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SendUserId", userInfo.getUserId());
        jsonObject.addProperty("Volume", Double.valueOf(volume));
        jsonObject.addProperty("Weight", Double.valueOf(kg));
        jsonObject.addProperty("Quantity", Integer.valueOf(quantity));
        jsonObject.addProperty("IsMove", isMove);
        jsonObject.addProperty("IsSurcharge", isSurcharge);
        jsonObject.addProperty("IsToPayFreight", isToPayFreight);
        jsonObject.addProperty("ToPayFreightTel", payFreightTel);
        jsonObject.addProperty("IsCollectionPayment", isCollectionPayment);
        jsonObject.addProperty("Payment", payment);
        jsonObject.addProperty("IsMyFleet", isMyFleet);
        jsonObject.addProperty("OrderAddress", orderAddress);
        jsonObject.addProperty("Premiums", premiums);
        jsonObject.addProperty("Remark", remark);
        jsonObject.addProperty("OrderType", type);
        jsonObject.addProperty("PlanSendTime", time);
        LogUtil.i(jsonObject.toString());
        mapJson = jsonObject.toString();
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        if (type == 3) {
            HttpUtil.doPost(MainActivity.this, Contants.url_publishCarpool, "publishCarpool", map, new VolleyInterface(MainActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                @Override
                public void onSuccess(JsonElement result) {
                    LogUtil.i(result.toString());

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
        } else {
            String money = tv_money.getText().toString();
            Intent intent = new Intent(MainActivity.this, ConfirmOrderActivity.class);
            intent.putExtra("type", type);
            intent.putExtra("mapJson", mapJson);
            intent.putExtra("money", money);
            intent.putExtra("time", time);
            intent.putExtra("carType", 2);
            intent.putParcelableArrayListExtra("OrderAddressInfos", mOrderAddressInfos);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("orderAddressInfo", mOrderAddressInfos.get(position));
        intent.putExtra("type", 3);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActionClick(int position) {
        mOrderAddressInfos.remove(position);
        mOrderAddressAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {

            int position = data.getIntExtra("position", 0);
            int type = data.getIntExtra("type", 0);
            OrderAddressInfo orderAddressInfo = data.getParcelableExtra("orderAddressInfo");

            if (type == 2) {
                OrderAddressInfo old = mOrderAddressInfos.get(position);
                mOrderAddressInfos.set(position, orderAddressInfo);
                mOrderAddressInfos.add(old);
                mOrderAddressAdapter.notifyDataSetChanged();
            } else {
                mOrderAddressInfos.set(position, orderAddressInfo);
            }
            mOrderAddressAdapter.notifyDataSetChanged();

            LogUtil.i(mOrderAddressInfos.toString());

            getOrderInfo();

            calculateFreight();


        }
        if (resultCode == RESULT_OK && requestCode == 2) {
            premiums = data.getDoubleExtra("premium", 0);
            double goods = data.getDoubleExtra("goods", 0);
            tv_goods.setText(String.format("%s元", goods));
            double money = freight + premiums;
            if (isPublish) {
                tv_money.setText(String.format("¥%s", money));
            }
        }


        if (resultCode == RESULT_OK && requestCode == 3) {
            userInfo = data.getParcelableExtra("userInfo");
            tv_tel.setText(userInfo.getUserName());
            HttpUtil.setImageLoader(Contants.imagehost + userInfo.getHeadIco(), civ_head, R.mipmap.account, R.mipmap.account);
        }

    }


    private void calculateFreight() {
        if (isPublish) {
            if (cofirmTypeId == -1) {
                showShortToastByString("未确定规格");
            } else if (cofirmTypeId == 0) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Volume", Double.valueOf(volume));
                jsonObject.addProperty("Weight", Double.valueOf(kg));
                jsonObject.addProperty("StartLngLat", startLngLat);
                jsonObject.addProperty("EndLngLat", endLngLat);
                LogUtil.i(jsonObject.toString());
                Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
                HttpUtil.doPost(MainActivity.this, Contants.url_lingDanFreight, "lingDanFreight", map, new VolleyInterface(MainActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                    @Override
                    public void onSuccess(JsonElement result) {
                        Gson gson = new Gson();
                        FreightInfo freightInfo = gson.fromJson(result, FreightInfo.class);
                        freight = freightInfo.getFreight();

                        double money = freight + premiums;
                        tv_money.setText(String.format("¥%s", money));

                        LogUtil.i("lingDanFreight" + result.toString());
                    }

                    @Override
                    public void onError(VolleyError error) {
                        showShortToastByString(getString(R.string.timeoutError));
                    }

                    @Override
                    public void onStateError() {
                    }
                });
            } else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("VehicleType", vehideTypeId);
                jsonObject.addProperty("StartLngLat", startLngLat);
                jsonObject.addProperty("EndLngLat", endLngLat);
                LogUtil.i(jsonObject.toString());
                Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
                HttpUtil.doPost(MainActivity.this, Contants.url_calculationFreight, "calculationFreight", map, new VolleyInterface(MainActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                    @Override
                    public void onSuccess(JsonElement result) {
                        LogUtil.i("CalculationFreight" + result.toString());
                        Gson gson = new Gson();
                        FreightInfo freightInfo = gson.fromJson(result, FreightInfo.class);
                        freight = freightInfo.getFreight();
                        double money = freight + premiums;
                        tv_money.setText(String.format("¥%s", money));
                    }

                    @Override
                    public void onError(VolleyError error) {

                        showShortToastByString(getString(R.string.timeoutError));
                    }

                    @Override
                    public void onStateError() {
                    }
                });
            }


        }
    }

    private void getOrderInfo() {
        orderAddress = "";
        startLngLat = "";
        endLngLat = "";
        payment = 0;
        isPublish = false;

        OrderAddressInfo orderAddressInfo;
        int size = mOrderAddressInfos.size();
        if (!mOrderAddressInfos.get(0).getReceiptAddress().equals("start") && !mOrderAddressInfos.get(size - 1).getReceiptAddress().equals("start")) {
            isPublish = true;
        }

        for (int i = 0; i < size; i++) {
            orderAddressInfo = mOrderAddressInfos.get(i);
            payment += orderAddressInfo.getMoney();
            if (i == 0) {
                orderAddress = orderAddressInfo.toString() + "|";
                startLngLat = String.valueOf(orderAddressInfo.getLng()) + "," + String.valueOf(orderAddressInfo.getLat());
            } else if (i == size - 1) {
                orderAddress = orderAddress + orderAddressInfo.toString();
                endLngLat = endLngLat + String.valueOf(orderAddressInfo.getLng()) + "," + String.valueOf(orderAddressInfo.getLat());
            } else {
                orderAddress = orderAddress + orderAddressInfo.toString() + "|";
                startLngLat = startLngLat + "|" + String.valueOf(orderAddressInfo.getLng()) + "," + String.valueOf(orderAddressInfo.getLat());
                endLngLat = endLngLat + String.valueOf(orderAddressInfo.getLng()) + "," + String.valueOf(orderAddressInfo.getLat()) + "|";
            }
        }

        LogUtil.i("startLngLat" + startLngLat);
        LogUtil.i("endLngLat" + endLngLat);
        LogUtil.i("orderAddress" + orderAddress);
        LogUtil.i("payment" + payment);
        LogUtil.i("isPublish" + isPublish);
    }

}
