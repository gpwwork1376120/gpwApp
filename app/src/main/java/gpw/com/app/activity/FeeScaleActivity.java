package gpw.com.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;

public class FeeScaleActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    @Override
    protected int getLayout() {
        return R.layout.activity_fee_scale;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.fee_scale);
        tv_right.setVisibility(View.GONE);
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
