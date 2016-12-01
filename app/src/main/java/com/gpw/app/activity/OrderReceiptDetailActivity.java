package com.gpw.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.gpw.app.R;
import com.gpw.app.base.BaseActivity;
import com.gpw.app.base.Contants;
import com.gpw.app.bean.OrderDetailInfo;
import com.gpw.app.bean.ReceiptOrderDetailInfo;
import com.gpw.app.bean.ReceiptOrderInfo;
import com.gpw.app.util.EncryptUtil;
import com.gpw.app.util.HttpUtil;
import com.gpw.app.util.LogUtil;
import com.gpw.app.util.VolleyInterface;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class OrderReceiptDetailActivity extends BaseActivity {
    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private String orderId;
    private TextView tv_time;
    private TextView tv_time1;
    private TextView tv_address;
    private TextView tv_address1;
    private Button bt_call;
    private Button bt_location;
    private Button bt_confirm;
    private Button bt_apliy;
    private ReceiptOrderDetailInfo receiptOrderDetailInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_order_receipt_detail;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);

        bt_call = (Button) findViewById(R.id.bt_call);
        bt_apliy = (Button) findViewById(R.id.bt_apliy);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
        bt_location = (Button) findViewById(R.id.bt_location);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_address1 = (TextView) findViewById(R.id.tv_address1);

    }

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra("orderId");
    }


    @Override
    protected void initView() {

        tv_title.setText(R.string.order_detail1);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId", Contants.userId);
        jsonObject.addProperty("OrderNo", orderId);
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        HttpUtil.doPost(OrderReceiptDetailActivity.this, Contants.url_getReceiptOrderDetail, "getReceiptOrderDetail", map, new VolleyInterface(OrderReceiptDetailActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i(result.toString());
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ReceiptOrderDetailInfo>>() {
                }.getType();
                ArrayList<ReceiptOrderDetailInfo> OrderDetailInfos = gson.fromJson(result, listType);
                receiptOrderDetailInfo = OrderDetailInfos.get(0);
                ArrayList<ReceiptOrderDetailInfo.OrderAddressBean> orderAddressBeen = (ArrayList<ReceiptOrderDetailInfo.OrderAddressBean>) receiptOrderDetailInfo.getOrderAddress();
                tv_time.setText(orderAddressBeen.get(0).getArriveTime());
                tv_time1.setText(orderAddressBeen.get(1).getArriveTime());
                tv_address.setText(String.format("%s  %s  %s", orderAddressBeen.get(0).getAddress(), orderAddressBeen.get(0).getReceipter(), orderAddressBeen.get(0).getTel()));
                tv_address1.setText(String.format("%s  %s  %s", orderAddressBeen.get(1).getAddress(), orderAddressBeen.get(1).getReceipter(), orderAddressBeen.get(1).getTel()));
                System.out.println(OrderDetailInfos.size());

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
        tv_right.setVisibility(View.GONE);
        iv_left_white.setOnClickListener(this);
        bt_call.setOnClickListener(this);
        bt_apliy.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
        bt_location.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
            case R.id.bt_apliy:
                break;
            case R.id.bt_confirm:
                break;
            case R.id.bt_location:
                Intent intent = new Intent(OrderReceiptDetailActivity.this, CarLocationActivity.class);
                intent.putExtra("TransporterId", receiptOrderDetailInfo.getTransporterId());
                intent.putExtra("TransporterName", receiptOrderDetailInfo.getTransporterName());
                startActivity(intent);
                break;
        }
    }


}
