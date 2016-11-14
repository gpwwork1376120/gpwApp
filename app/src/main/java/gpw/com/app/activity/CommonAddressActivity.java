package gpw.com.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import gpw.com.app.adapter.CommonAdInfoAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.CommonAdInfo;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;
import gpw.com.app.view.XRecyclerView;

import static gpw.com.app.R.id.tv_right;

public class CommonAddressActivity extends BaseActivity {

    private TextView tv_title;
    private ImageView iv_right;
    private ImageView iv_left_white;
    private String userId;
    private XRecyclerView rv_common_ad;
    private CommonAdInfoAdapter commonAdInfoAdapter;
    private ArrayList<CommonAdInfo> commonAdInfos;

    @Override
    protected int getLayout() {
        return R.layout.activity_common_address;
    }

    @Override
    protected void findById() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_left_white = (ImageView) findViewById(R.id.iv_left_white);
        rv_common_ad = (XRecyclerView) findViewById(R.id.rv_main_address);
    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("UserId");
        commonAdInfos = new ArrayList<>();
        commonAdInfoAdapter = new CommonAdInfoAdapter(this, commonAdInfos);
    }

    @Override
    protected void initView() {

        rv_common_ad.setAdapter(commonAdInfoAdapter);
        getUserAddress(1,0);
        rv_common_ad.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getUserAddress(1,0);
            }

            @Override
            public void onLoadMore() {
                getUserAddress(1,1);
            }
        });

        tv_title.setText(R.string.common_address);
        iv_left_white.setOnClickListener(this);
        iv_right.setOnClickListener(this);


    }

    private void getUserAddress(int PageIndex, final int ways) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId", userId);
        jsonObject.addProperty("PageIndex", 1);
        jsonObject.addProperty("PageSize", 15);
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());

        HttpUtil.doPost(CommonAddressActivity.this, Contants.url_getUserAddress, "getUserAddress", map, new VolleyInterface(CommonAddressActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<CommonAdInfo>>() {
                }.getType();
                ArrayList<CommonAdInfo> newCommonAdInfos = gson.fromJson(result, listType);
                if (ways == 0) {
                    rv_common_ad.refreshComplete("success");
                    commonAdInfos.clear();
                    commonAdInfos.addAll(newCommonAdInfos);
                } else {
                    rv_common_ad.loadMoreComplete();
                    if (newCommonAdInfos.size() < 15) {
                        rv_common_ad.setNoMore(true);
                    }
                    commonAdInfos.addAll(newCommonAdInfos);
                }
                commonAdInfoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                LogUtil.i("register", error.networkResponse.headers.toString());
                LogUtil.i("register", error.networkResponse.statusCode + "");

                if (ways == 0) {
                    rv_common_ad.refreshComplete("fail");
                } else {
                    rv_common_ad.loadMoreComplete();
                }

            }

            @Override
            public void onStateError() {
                if (ways == 0) {
                    rv_common_ad.refreshComplete("fail");
                } else {
                    rv_common_ad.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
            case tv_right:

                break;
        }
    }
}
