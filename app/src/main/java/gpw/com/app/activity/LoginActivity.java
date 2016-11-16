package gpw.com.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.UserInfo;
import gpw.com.app.util.DateUtil;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.MD5Util;
import gpw.com.app.util.NetworkUtil;
import gpw.com.app.util.VolleyInterface;
import gpw.com.app.view.CustomProgressDialog;

public class LoginActivity extends BaseActivity {

    private EditText et_account;
    private EditText et_password;
    private ImageView iv_close;
    private ImageView iv_login_eye;
    private TextView tv_forget_psd;
    private TextView tv_register;
    private Button bt_login;

    private SharedPreferences prefs;

    private String password;
    private String account;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void findById() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_login_eye = (ImageView) findViewById(R.id.iv_login_eye);
        tv_forget_psd = (TextView) findViewById(R.id.tv_forget_psd);
        tv_register = (TextView) findViewById(R.id.tv_register);
        bt_login = (Button) findViewById(R.id.bt_login);
    }

    @Override
    protected void initData() {
        prefs = getSharedPreferences(Contants.SHARED_NAME, MODE_PRIVATE);
        account = prefs.getString("account", "");
        password = prefs.getString("password", "");
    }

    @Override
    protected void initView() {
        et_password.setText(password);
        et_account.setText(account);
        iv_close.setOnClickListener(this);
        iv_login_eye.setOnClickListener(this);
        tv_forget_psd.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_login:
                if (NetworkUtil.isConnected(LoginActivity.this)) {
                    login();
                }else {
                    showShortToastByString(getString(R.string.Neterror));
                }
                break;
            case R.id.iv_login_eye:
                break;
            case R.id.iv_close:
                break;
            case R.id.tv_forget_psd:
                intent.setClass(LoginActivity.this, RebuildPsdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 0);
                break;


        }

    }

    private void login() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(LoginActivity.this);
        customProgressDialog.show();
        customProgressDialog.setText("登录中..");
        account = et_account.getText().toString();
        String time = DateUtil.getPsdCurrentDate();
        password = et_password.getText().toString();
        if (account.isEmpty()||password.isEmpty()){
            showShortToastByString("信息不完整");
            return;
        }
        String finalPassword = MD5Util.encrypt(time + password);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("LoginName", account);
        jsonObject.addProperty("Time", time);
        jsonObject.addProperty("Password", finalPassword);
        jsonObject.addProperty("UserType", 1);
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());

        HttpUtil.doPost(LoginActivity.this, Contants.url_userLogin, "login", map, new VolleyInterface(LoginActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i("hint", result.toString());
                showShortToastByString("登录成功");
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(result,UserInfo.class);
                customProgressDialog.dismiss();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("account", account);
                editor.putString("password", password);
                editor.putString("UserId", userInfo.getUserId());
                editor.putString("UserName", userInfo.getUserName());
                editor.putString("Tel", userInfo.getTel());
                editor.putString("HeadIco", userInfo.getHeadIco());
                editor.putString("Sex", userInfo.getSex());
                editor.putString("Address", userInfo.getAddress());
                editor.apply();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                intent.putExtra("userInfo",userInfo);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(VolleyError error) {
                customProgressDialog.dismiss();
                showShortToastByString(getString(R.string.timeoutError));
                LogUtil.i("hint", error.toString());
            }

            @Override
            public void onStateError() {
                customProgressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String account = data.getStringExtra("account");
            String password = data.getStringExtra("password");
            et_account.setText(account);
            et_password.setText(password);
        }
    }


}
