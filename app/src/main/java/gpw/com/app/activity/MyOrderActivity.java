package gpw.com.app.activity;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;

public class MyOrderActivity extends BaseActivity {
    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;
    private TextView tv_all;
    private TextView tv_new;
    private TextView tv_doing;
    private TextView tv_end;
    private View view_all;
    private View view_new;
    private View view_doing;
    private View view_end;
    private Button bt_query;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(R.id.in_head);
        LinearLayout test = (LinearLayout) findViewById(R.id.test1);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(R.id.iv_left_white);
        bt_query = (Button) test.findViewById(R.id.bt_query);
        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_new = (TextView) findViewById(R.id.tv_new);
        tv_doing = (TextView) findViewById(R.id.tv_doing);
        tv_end = (TextView) findViewById(R.id.tv_end);
        view_all = findViewById(R.id.view_all);
        view_new = findViewById(R.id.view_new);
        view_doing = findViewById(R.id.view_doing);
        view_end = findViewById(R.id.view_end);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.myOrder);
        tv_right.setVisibility(View.GONE);
        iv_left_white.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_new.setOnClickListener(this);
        tv_doing.setOnClickListener(this);
        tv_end.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left_white:
                finish();
                break;
            case R.id.bt_query:
                Intent intent = new Intent(MyOrderActivity.this, OrderDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_all:
                choiceState(1);
                break;
            case R.id.tv_new:
                choiceState(2);
                break;
            case R.id.tv_doing:
                choiceState(3);
                break;
            case R.id.tv_end:
                choiceState(4);
                break;
        }
    }
    private void choiceState(int i){
        initState();
        switch (i) {
            case 1:
                tv_all.setTextColor(ContextCompat.getColor(MyOrderActivity.this, R.color.color_red));
                view_all.setVisibility(View.VISIBLE);
              break;
            case 2:
                tv_new.setTextColor(ContextCompat.getColor(MyOrderActivity.this, R.color.color_red));
                view_new.setVisibility(View.VISIBLE);
              break;
            case 3:
                tv_doing.setTextColor(ContextCompat.getColor(MyOrderActivity.this, R.color.color_red));
                view_doing.setVisibility(View.VISIBLE);
              break;
            case 4:
                tv_end.setTextColor(ContextCompat.getColor(MyOrderActivity.this, R.color.color_red));
                view_end.setVisibility(View.VISIBLE);
              break;
        }
    }

    private void initState() {
        view_all.setVisibility(View.INVISIBLE);
        view_new.setVisibility(View.INVISIBLE);
        view_doing.setVisibility(View.INVISIBLE);
        view_end.setVisibility(View.INVISIBLE);
        tv_all.setTextColor(ContextCompat.getColor(MyOrderActivity.this, R.color.color_gary_font));
        tv_new.setTextColor(ContextCompat.getColor(MyOrderActivity.this, R.color.color_gary_font));
        tv_doing.setTextColor(ContextCompat.getColor(MyOrderActivity.this, R.color.color_gary_font));
        tv_end.setTextColor(ContextCompat.getColor(MyOrderActivity.this, R.color.color_gary_font));
    }
}
