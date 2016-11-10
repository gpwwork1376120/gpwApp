package gpw.com.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;

public class MyWalletActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private Button bt_recharge;
    private Button bt_charge_situation;
    private LinearLayout ll_recharge;
    private TextView tv_balance_money;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        bt_recharge = (Button) findViewById(R.id.bt_recharge);
        bt_charge_situation = (Button) findViewById(R.id.bt_charge_situation);
        ll_recharge = (LinearLayout) findViewById(R.id.ll_recharge);
        tv_balance_money = (TextView) findViewById(R.id.tv_balance_money);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        tv_right.setVisibility(View.GONE);
        tv_title.setText(R.string.myWallet);


        iv_left_white.setOnClickListener(this);
        bt_charge_situation.setOnClickListener(this);
        bt_recharge.setOnClickListener(this);
        ll_recharge.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.iv_left_white:
                finish();
                break;
            case R.id.bt_charge_situation:
                intent.setClass(MyWalletActivity.this,ChargeScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_recharge:
                intent.setClass(MyWalletActivity.this,RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_recharge:
                intent.setClass(MyWalletActivity.this,RechargeHintActivity.class);
                startActivity(intent);
                break;

        }
    }
}
