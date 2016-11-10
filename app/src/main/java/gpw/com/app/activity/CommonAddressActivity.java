package gpw.com.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;

import static gpw.com.app.R.id.tv_right;

public class CommonAddressActivity extends BaseActivity {

    private TextView tv_title;
    private ImageView iv_right;
    private ImageView iv_left_white;
    @Override
    protected int getLayout() {
        return R.layout.activity_common_address;
    }

    @Override
    protected void findById() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        iv_left_white = (ImageView) findViewById(R.id.iv_left_white);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.common_address);
        iv_left_white.setOnClickListener(this);
        iv_right.setOnClickListener(this);
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
