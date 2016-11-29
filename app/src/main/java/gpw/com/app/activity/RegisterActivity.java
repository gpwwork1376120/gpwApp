package gpw.com.app.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.NetworkUtil;
import gpw.com.app.util.VolleyInterface;
import gpw.com.app.view.CustomProgressDialog;

public class RegisterActivity extends BaseActivity {


    private EditText et_account;
    private EditText et_password;
    private EditText et_validate_code;
    private ImageView iv_close;
    private TextView tv_get_code;
    private Button bt_ok;
    private CheckBox cb_eye;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void findById() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        et_validate_code = (EditText) findViewById(R.id.et_validate_code);
        iv_close = (ImageView) findViewById(R.id.iv_close);

        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        bt_ok = (Button) findViewById(R.id.bt_ok);
        cb_eye = (CheckBox) findViewById(R.id.cb_eye);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        iv_close.setOnClickListener(this);

        tv_get_code.setOnClickListener(this);
        bt_ok.setOnClickListener(this);

        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int size = s.length();
                if (size != 0) {
                    bt_ok.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.button_red_bg));
                } else {
                    bt_ok.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.button_login));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cb_eye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                if (NetworkUtil.isConnected(RegisterActivity.this)) {
                    register();
                }else {
                    showShortToastByString(getString(R.string.Neterror));
                }
                break;

            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_get_code:
                getCheckCode();
                break;

        }
    }

    private void getCheckCode() {
        String account = et_account.getText().toString();
        if (account.isEmpty()){
            showShortToastByString("信息不完整");
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Tel",account);
        Map<String,String> map = EncryptUtil.encryptDES(jsonObject.toString());

        HttpUtil.doPost(RegisterActivity.this, Contants.url_obtainCheckCode, "obtainCheckCode", map, new VolleyInterface(RegisterActivity.this,VolleyInterface.mListener,VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                showShortToastByString("获取成功");
                LogUtil.i("register",result.toString());
            }

            @Override
            public void onError(VolleyError error) {
                LogUtil.i("hint", error.toString());
//                LogUtil.i("register",error.networkResponse.headers.toString());
//                LogUtil.i("register",error.networkResponse.statusCode+"");
            }

            @Override
            public void onStateError() {

            }
        });


    }

    private void register() {
        final String account = et_account.getText().toString();
        final String password = et_password.getText().toString();
        String validateCode = et_validate_code.getText().toString();
        if (account.isEmpty()||password.isEmpty()||validateCode.isEmpty()){
            showShortToastByString("信息不完整");
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Phone",account);
        jsonObject.addProperty("VerificationCode",validateCode);
        jsonObject.addProperty("Password",password);
        jsonObject.addProperty("UserType",1);
        Map<String,String> map = EncryptUtil.encryptDES(jsonObject.toString());

        HttpUtil.doPost(RegisterActivity.this, Contants.url_register, "register", map, new VolleyInterface(RegisterActivity.this,VolleyInterface.mListener,VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i(result.toString());
                showShortToastByString("注册成功");
                getIntent().putExtra("Phone",account);
                getIntent().putExtra("Password",password);
                setResult(RESULT_OK,getIntent());
                finish();
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
}
