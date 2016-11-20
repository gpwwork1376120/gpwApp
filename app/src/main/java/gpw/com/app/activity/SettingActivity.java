package gpw.com.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.bean.UserInfo;

public class SettingActivity extends BaseActivity {


    private RelativeLayout rl_account_management;
    private RelativeLayout rl_fee_scale;
    private RelativeLayout rl_faq;
    private RelativeLayout rl_feed_back;
    private RelativeLayout rl_about_us;
    private CheckBox cb_vibrates;
    private CheckBox cb_sound;
    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private UserInfo userInfo;
    private boolean isChange;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        rl_account_management = (RelativeLayout) findViewById(R.id.rl_account_management);
        rl_fee_scale = (RelativeLayout) findViewById(R.id.rl_fee_scale);
        rl_feed_back = (RelativeLayout) findViewById(R.id.rl_feed_back);
        rl_faq = (RelativeLayout) findViewById(R.id.rl_faq);
        rl_about_us = (RelativeLayout) findViewById(R.id.rl_about_us);
        cb_vibrates = (CheckBox) findViewById(R.id.cb_vibrates);
        cb_sound = (CheckBox) findViewById(R.id.cb_sound);
    }

    @Override
    protected void initData() {
        isChange = false;
        userInfo = getIntent().getParcelableExtra("userInfo");
    }

    @Override
    protected void initView() {
        tv_right.setVisibility(View.GONE);
        tv_title.setText(R.string.setting);
        rl_account_management.setOnClickListener(this);
        rl_fee_scale.setOnClickListener(this);
        rl_feed_back.setOnClickListener(this);
        rl_faq.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        iv_left_white.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_left_white:
                if (isChange) {
                    getIntent().putExtra("userInfo", userInfo);
                    setResult(RESULT_OK, getIntent());
                }
                finish();
                break;
            case R.id.rl_account_management:
                intent.setClass(SettingActivity.this, PersonalInfoActivity.class);
                intent.putExtra("userInfo", userInfo);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_fee_scale:
                intent.setClass(SettingActivity.this, FeeScaleActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_faq:
                intent.setClass(SettingActivity.this, FAQActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_feed_back:
                intent.setClass(SettingActivity.this, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about_us:
                intent.setClass(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        if (isChange) {
            getIntent().putExtra("userInfo", userInfo);
            setResult(RESULT_OK, getIntent());
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            isChange = true;
            userInfo = data.getParcelableExtra("userInfo");
        }
    }
}
