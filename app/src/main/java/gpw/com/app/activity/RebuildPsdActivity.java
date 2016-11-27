package gpw.com.app.activity;


import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.base.Contants;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;


public class RebuildPsdActivity extends BaseActivity {



    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private EditText et_account;
    private EditText et_password;
    private TextView get_validate_code;
    private EditText et_validate_code;
    private CheckBox cb_eye;

    @Override
    protected int getLayout() {
        return R.layout.activity_rebuild_psd;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        cb_eye = (CheckBox) findViewById(R.id.cb_eye);
        et_password = (EditText) findViewById(R.id.et_password);
        et_account = (EditText) findViewById(R.id.et_account);
        get_validate_code = (TextView) findViewById(R.id.get_validate_code);
        et_validate_code = (EditText) findViewById(R.id.et_validate_code);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.rebuild_psd);
        tv_right.setText("提交");
        iv_left_white.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        get_validate_code.setOnClickListener(this);
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


    private void getCheckCode() {
        String account = et_account.getText().toString();
        if (account.isEmpty()){
            showShortToastByString("信息不完整");
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Tel",account);
        Map<String,String> map = EncryptUtil.encryptDES(jsonObject.toString());

        HttpUtil.doPost(RebuildPsdActivity.this, Contants.url_obtainCheckCode, "obtainCheckCode", map, new VolleyInterface(RebuildPsdActivity.this,VolleyInterface.mListener,VolleyInterface.mErrorListener) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left_white:
                finish();
                break;
            case R.id.tv_right:
                editPassWord();
                break;
            case R.id.get_validate_code:
                getCheckCode();
                break;

        }

    }

    private void editPassWord() {
        final String account = et_account.getText().toString();
        final String password = et_password.getText().toString();
        String validateCode = et_validate_code.getText().toString();
        if (account.isEmpty()||password.isEmpty()||validateCode.isEmpty()){
            showShortToastByString("信息不完整");
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Tel",account);
        jsonObject.addProperty("CheckCode",validateCode);
        jsonObject.addProperty("Password",password);
        jsonObject.addProperty("UserType",1);
        Map<String,String> map = EncryptUtil.encryptDES(jsonObject.toString());

        HttpUtil.doPost(RebuildPsdActivity.this, Contants.url_updatePassWord, "updatePassWord", map, new VolleyInterface(RebuildPsdActivity.this,VolleyInterface.mListener,VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i(result.toString());
                showShortToastByString("修改成功");
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
