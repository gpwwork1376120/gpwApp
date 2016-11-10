package gpw.com.app.activity;

import android.view.View;
import android.widget.ImageView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;

public class ChargeScheduleActivity extends BaseActivity {

    private ImageView iv_left_white;

    @Override
    protected int getLayout() {
        return R.layout.activity_charge_schedule;
    }

    @Override
    protected void findById() {
        iv_left_white = (ImageView) findViewById(R.id.iv_left_white);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        iv_left_white.setOnClickListener(this);
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
