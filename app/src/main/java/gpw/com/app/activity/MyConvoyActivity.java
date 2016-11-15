package gpw.com.app.activity;

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
import gpw.com.app.adapter.ConvoyAdapter;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.CommonAdInfo;
import gpw.com.app.bean.ConvoyInfo;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;

public class MyConvoyActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private ListView lv_my_convoy;
    private ConvoyAdapter mConvoyAdapter;
    ArrayList<ConvoyInfo> mConvoyInfos;
    private String userId;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_convoy;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        lv_my_convoy = (ListView) findViewById(R.id.lv_my_convoy);
    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");
        mConvoyInfos = new ArrayList<>();
        mConvoyAdapter = new ConvoyAdapter(mConvoyInfos,this);
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.myConvoy);
        tv_right.setText(R.string.select);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId",userId);
        Map<String,String> map = EncryptUtil.encryptDES(jsonObject.toString());

        HttpUtil.doPost(MyConvoyActivity.this, Contants.url_getUserVehicleTeam, "getUserVehicleTeam", map, new VolleyInterface(MyConvoyActivity.this,VolleyInterface.mListener,VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i(result.toString());
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ConvoyInfo>>() {
                }.getType();
                ArrayList<ConvoyInfo> convoyInfos = gson.fromJson(result,listType);
                mConvoyInfos.addAll(convoyInfos);
                mConvoyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                LogUtil.i(error.toString());
//                LogUtil.i("register",error.networkResponse.headers.toString());
//                LogUtil.i("register",error.networkResponse.statusCode+"");
            }

            @Override
            public void onStateError() {

            }
        });
        iv_left_white.setOnClickListener(this);
        tv_right.setOnClickListener(this);
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
