package gpw.com.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;

public class MyInvoiceActivity extends BaseActivity {

    private Button bt_ok;
    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_invoice;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        bt_ok = (Button) findViewById(R.id.bt_ok);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.myInvoice);
        tv_right.setText(R.string.invoice_rule);
        iv_left_white.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
            case R.id.tv_right:
                intent.setClass(MyInvoiceActivity.this,InvoiceRuleActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_ok:
                intent.setClass(MyInvoiceActivity.this,ApplyInvoiceActivity.class);
                startActivity(intent);
                break;

        }
    }
}
