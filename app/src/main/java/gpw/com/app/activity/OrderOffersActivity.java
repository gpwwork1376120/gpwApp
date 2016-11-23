package gpw.com.app.activity;

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
import gpw.com.app.adapter.OrderOffersAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.MoneyInfo;
import gpw.com.app.bean.NewsInfo;
import gpw.com.app.bean.OrderOfferInfo;
import gpw.com.app.util.BdMapLocationUtil;
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

    @Override
    protected void initData() {
        orderOfferInfos =  new ArrayList<>();
        orderOffersAdapter =  new OrderOffersAdapter(orderOfferInfos,this);
        orderId = getIntent().getStringExtra("orderId");
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.order_offers);
        lv_order_offers.setAdapter(orderOffersAdapter);

        BdMapLocationUtil.getInstance(this).startLocation(new BdMapLocationUtil.BdLocationSuccessListenner() {
            @Override
            public void locationResult(double _latitude, double _longitude, String _locationAddr, String _locationCity) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("UserId", Contants.userId);
                jsonObject.addProperty("OrderNo", orderId);
                jsonObject.addProperty("Lat", _latitude);
                jsonObject.addProperty("Lng", _longitude);
                Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
                HttpUtil.doPost(OrderOffersActivity.this, Contants.url_getOrderOffers, "getUserBalance", map, new VolleyInterface(OrderOffersActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                    @Override
                    public void onSuccess(JsonElement result) {
                        LogUtil.i(result.toString());
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
        });

        lv_order_offers.setEmptyView(tv_empty);
        tv_right.setVisibility(View.GONE);
        iv_left_white.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
        }
    }
}
