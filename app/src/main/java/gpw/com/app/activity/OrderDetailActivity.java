package gpw.com.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.adapter.OrderDetailAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.MoneyInfo;
import gpw.com.app.bean.NewsInfo;
import gpw.com.app.bean.OrderDetailInfo;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;

public class OrderDetailActivity extends BaseActivity {
    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private String orderId;
    private ListView lv_order_detail;
    private OrderDetailInfo orderDetailInfo;
    private OrderDetailAdapter orderDetailAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        lv_order_detail = (ListView) findViewById(R.id.lv_order_detail);

    }

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra("orderId");

    }

    @Override
    protected void initView() {

        tv_title.setText(R.string.order_detail);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId", Contants.userId);
        jsonObject.addProperty("OrderNo", orderId);
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        HttpUtil.doPost(OrderDetailActivity.this, Contants.url_getSendOrderDetail, "getSendOrderDetail", map, new VolleyInterface(OrderDetailActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i(result.toString());
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<OrderDetailInfo>>() {
                }.getType();
                ArrayList<OrderDetailInfo> OrderDetailInfos = gson.fromJson(result, listType);
                orderDetailInfo = OrderDetailInfos.get(0);
                orderDetailAdapter = new OrderDetailAdapter(orderDetailInfo,OrderDetailActivity.this);
                orderDetailAdapter.setOnBtnClickListener(new OrderDetailAdapter.OnBtnClickListener() {
                    @Override
                    public void onBtnClick(int position, String viewName) {
                        switch (viewName){
                            case "呼叫车主":
                                break;
                            case "取消订单":
                                break;
                            case "确认卸货":
                                break;
                            case "确认收货":
                                break;
                            case "车辆定位":
                                Intent intent = new Intent(OrderDetailActivity.this,CarLocationActivity.class);
                                intent.putExtra("TransporterId",orderDetailInfo.getTransporterId());
                                startActivity(intent);
                                break;
                            case "收藏至车队":
                                keepTransporter(orderDetailInfo.getTransporterId());
                                break;
                        }
                    }
                });

                System.out.println(OrderDetailInfos.size());
                lv_order_detail.setAdapter(orderDetailAdapter);
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

    }

    private void keepTransporter(String transportUserId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SendUserId", Contants.userId);
        jsonObject.addProperty("TransportUserId", transportUserId);
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        HttpUtil.doPost(OrderDetailActivity.this, Contants.url_keepTransporter, "keepTransporter", map, new VolleyInterface(OrderDetailActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i(result.toString());
                showShortToastByString(result.toString());
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
        }
    }
}
