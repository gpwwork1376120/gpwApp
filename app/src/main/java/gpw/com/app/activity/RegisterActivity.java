package gpw.com.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.view.CustomProgressDialog;

public class RegisterActivity extends BaseActivity {


    private EditText et_account;
    private EditText et_password;
    private EditText et_validate_code;
    private ImageView iv_close;
    private ImageView iv_login_eye;
    private TextView tv_get_code;
    private Button bt_ok;

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
        iv_login_eye = (ImageView) findViewById(R.id.iv_login_eye);
        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        bt_ok = (Button) findViewById(R.id.bt_ok);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        iv_close.setOnClickListener(this);
        iv_login_eye.setOnClickListener(this);
        tv_get_code.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                finish();
                break;
            case R.id.iv_login_eye:
                break;
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_get_code:
                break;

        }
    }
}
