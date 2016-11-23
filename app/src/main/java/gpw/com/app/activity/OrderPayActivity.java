package gpw.com.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.adapter.OrderAddAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.JsonInfo;
import gpw.com.app.bean.OrderAddressInfo;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;

public class OrderPayActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_right;
    private TextView tv_orderType;
    private TextView tv_money;
    private TextView tv_time;
    private ImageView iv_left_white;

    private RelativeLayout rl_wallet;
    private RelativeLayout rl_wechat;
    private RelativeLayout rl_alipay;
    private RelativeLayout rl_card;
    private CheckBox cb_wallet;
    private CheckBox cb_wechat;
    private CheckBox cb_alipay;
    private CheckBox cb_card;
    private Button bt_recharge;
    private TextView tv_balance;
    private ListView lv_address;
    private OrderAddAdapter orderAddAdapter;
    private int orderType;
    private int carType;
    private JsonObject jsonObject;
    private String money;
    private String time;
    private int payType = 1;

    @Override
    protected int getLayout() {
        return R.layout.activity_order_pay;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);

        tv_balance = (TextView) findViewById(R.id.tv_balance);
        rl_wallet = (RelativeLayout) findViewById(R.id.rl_wallet);
        rl_wechat = (RelativeLayout) findViewById(R.id.rl_wechat);
        rl_alipay = (RelativeLayout) findViewById(R.id.rl_alipay);
        rl_card = (RelativeLayout) findViewById(R.id.rl_card);
        cb_wallet = (CheckBox) findViewById(R.id.cb_wallet);
        cb_wechat = (CheckBox) findViewById(R.id.cb_wechat);
        cb_alipay = (CheckBox) findViewById(R.id.cb_alipay);
        cb_card = (CheckBox) findViewById(R.id.cb_card);
        bt_recharge = (Button) findViewById(R.id.bt_recharge);
        lv_address = (ListView) findViewById(R.id.lv_address);
        tv_orderType = (TextView) findViewById(R.id.tv_orderType);
        tv_money = (TextView) findViewById(R.id.tv_money);
        tv_time = (TextView) findViewById(R.id.tv_time);


    }

    @Override
    protected void initData() {
        ArrayList<OrderAddressInfo> orderAddressInfos = getIntent().getParcelableArrayListExtra("OrderAddressInfos");
        orderType = getIntent().getIntExtra("type", 0);
        carType = getIntent().getIntExtra("carType", 0);
        String mapJson = getIntent().getStringExtra("mapJson");
        jsonObject = new JsonParser().parse(mapJson).getAsJsonObject();
        money = getIntent().getStringExtra("money");
        time = getIntent().getStringExtra("time");
        orderAddAdapter = new OrderAddAdapter(orderAddressInfos, this);
    }

    @Override
    protected void initView() {

        if (orderType == 1) {
            tv_orderType.setText("即");
        } else if (orderType == 2) {
            tv_orderType.setText("预");
        }

        tv_money.setText(money);
        tv_time.setText(time);
        lv_address.setAdapter(orderAddAdapter);
        tv_title.setText(R.string.order_pay);
        tv_right.setVisibility(View.GONE);
        iv_left_white.setOnClickListener(this);

        rl_wallet.setOnClickListener(this);
        rl_wechat.setOnClickListener(this);
        rl_alipay.setOnClickListener(this);
        rl_card.setOnClickListener(this);

        cb_wallet.setOnClickListener(this);
        cb_wechat.setOnClickListener(this);
        cb_alipay.setOnClickListener(this);
        cb_card.setOnClickListener(this);
        bt_recharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
            case R.id.rl_wallet:
            case R.id.cb_wallet:
                initRadio();
                cb_wallet.setChecked(true);
                payType =1;
                break;
            case R.id.rl_wechat:
            case R.id.cb_wechat:
                initRadio();
                cb_wechat.setChecked(true);
                payType =2;
                break;
            case R.id.rl_alipay:
            case R.id.cb_alipay:
                initRadio();
                cb_alipay.setChecked(true);
                payType =3;
                break;
            case R.id.rl_card:
            case R.id.cb_card:
                initRadio();
                cb_card.setChecked(true);
                payType =4;
                break;
            case R.id.bt_recharge:
                publishOrder();
                break;
        }
    }

    private void publishOrder() {
        jsonObject.addProperty("PayWay",payType);
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        if (carType == 1) {

            HttpUtil.doPost(OrderPayActivity.this, Contants.url_sendOrder, "sendOrder", map, new VolleyInterface(OrderPayActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                @Override
                public void onSuccess(JsonElement result) {
                    LogUtil.i(result.toString());
                    showShortToastByString("货源发布成功");
                    Intent intent = new Intent(OrderPayActivity.this, MyOrderActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(VolleyError error) {
                    showShortToastByString(getString(R.string.timeoutError));
                }

                @Override
                public void onStateError() {
                }
            });
        } else if (carType == 2) {
            HttpUtil.doPost(OrderPayActivity.this, Contants.url_publishCarpool, "publishCarpool", map, new VolleyInterface(OrderPayActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                @Override
                public void onSuccess(JsonElement result) {
                    LogUtil.i(result.toString());
                    showShortToastByString("货源发布成功");
                    Intent intent = new Intent(OrderPayActivity.this, MyOrderActivity.class);
                    startActivity(intent);
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

    private void initRadio() {
        cb_wallet.setChecked(false);
        cb_wechat.setChecked(false);
        cb_alipay.setChecked(false);
        cb_card.setChecked(false);
    }
}
