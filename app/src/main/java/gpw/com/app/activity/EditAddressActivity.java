package gpw.com.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.AddressID;
import gpw.com.app.bean.AddressMainInfo;
import gpw.com.app.bean.CommonAdInfo;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;

public class EditAddressActivity extends BaseActivity {
    private int pst;
    private int type;
    private CommonAdInfo commonAdInfo;
    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private EditText et_contact_name;
    private EditText et_contact_tel;
    private LinearLayout ll_address;
    private TextView tv_address;
    private Button bt_ok;
    private String userId;

    @Override
    protected int getLayout() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        et_contact_name = (EditText) findViewById(R.id.et_contact_name);
        et_contact_tel = (EditText) findViewById(R.id.et_contact_tel);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);

        tv_address = (TextView) findViewById(R.id.tv_address);
        bt_ok = (Button) findViewById(R.id.bt_ok);
    }

    @Override
    protected void initData() {
        pst = getIntent().getIntExtra("position", 0);
        type = getIntent().getIntExtra("type", 0);
        commonAdInfo = getIntent().getParcelableExtra("commonAdInfo");
        userId = getIntent().getStringExtra("userId");
    }

    @Override
    protected void initView() {
        if (type==1){
            et_contact_name.setText(commonAdInfo.getReceipter());
            et_contact_tel.setText(commonAdInfo.getReceiptTel());
        }
        tv_address.setText(commonAdInfo.getReceiptAddress());
        tv_title.setText("编辑常用地址");
        tv_right.setVisibility(View.GONE);
        iv_left_white.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_black:
                finish();
                break;
            case R.id.ll_address:
                finish();
                break;
            case R.id.bt_ok:
                String name = et_contact_name.getText().toString();
                String tel = et_contact_tel.getText().toString();
                commonAdInfo.setReceipter(name);
                commonAdInfo.setReceiptTel(tel);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("UserId", userId);
                jsonObject.addProperty("Address", commonAdInfo.getReceiptAddress());
                jsonObject.addProperty("Contacts", name);
                jsonObject.addProperty("Tel", tel);
                jsonObject.addProperty("Lat", commonAdInfo.getLat());
                jsonObject.addProperty("Lng", commonAdInfo.getLng());
                LogUtil.i(jsonObject.toString());
                if (type == 0) {
                    Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
                    HttpUtil.doPost(EditAddressActivity.this, Contants.url_saveUserAddress, "saveUserAddress", map, new VolleyInterface(EditAddressActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                        @Override
                        public void onSuccess(JsonElement result) {
                            LogUtil.i(result.toString());
                            showShortToastByString("添加成功");
                            Gson gson = new Gson();
                            AddressID addressID = gson.fromJson(result, AddressID.class);
                            commonAdInfo.setAddressId(addressID.getAddressId());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            showShortToastByString(getString(R.string.timeoutError));
//                          LogUtil.i("hint",error.networkResponse.headers.toString());
//                          LogUtil.i("hint",error.networkResponse.statusCode+"");
                        }

                        @Override
                        public void onStateError() {

                        }
                    });

                } else {
                    jsonObject.addProperty("AddressId",commonAdInfo.getAddressId());
                    LogUtil.i(jsonObject.toString());
                    Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
                    HttpUtil.doPost(EditAddressActivity.this, Contants.url_editUserAddress, "editUserAddress", map, new VolleyInterface(EditAddressActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                        @Override
                        public void onSuccess(JsonElement result) {
                            LogUtil.i(result.toString());
                            showShortToastByString("编辑成功");
                        }

                        @Override
                        public void onError(VolleyError error) {
                            showShortToastByString(getString(R.string.timeoutError));
//                          LogUtil.i("hint",error.networkResponse.headers.toString());
//                          LogUtil.i("hint",error.networkResponse.statusCode+"");
                        }

                        @Override
                        public void onStateError() {

                        }
                    });
                }
                getIntent().putExtra("position", pst);
                getIntent().putExtra("commonAdInfo", commonAdInfo);
                getIntent().putExtra("type", type);
                setResult(RESULT_OK, getIntent());
                finish();
                break;
        }
    }
}