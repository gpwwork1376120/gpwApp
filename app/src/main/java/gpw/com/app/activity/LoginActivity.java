package gpw.com.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.view.CustomProgressDialog;

public class LoginActivity extends BaseActivity {


    private EditText et_account;
    private EditText et_password;
    private ImageView iv_close;
    private ImageView iv_login_eye;
    private TextView tv_forget_psd;
    private TextView tv_register;
    private Button bt_login;
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

    }

    @Override
    protected void initView() {



        iv_close.setOnClickListener(this);
        iv_login_eye.setOnClickListener(this);
        tv_forget_psd.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.bt_login:
                final CustomProgressDialog customProgressDialog = new CustomProgressDialog(LoginActivity.this);
                customProgressDialog.show();
                customProgressDialog.setText("登录中..");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                customProgressDialog.dismiss();
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }).start();
//                intent.setClass(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_login_eye:
                break;
            case R.id.iv_close:

                break;
            case R.id.tv_forget_psd:
               // Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.setClass(LoginActivity.this,RebuildPsdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;



        }

    }
}
