package gpw.com.app.view;

import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gpw.com.app.R;
import gpw.com.app.base.BaseActivity;
import gpw.com.app.fragment.DescriptionFragment;


public class MainPopupWindow extends PopupWindow implements OnClickListener {

    private LinearLayout ll_car_1;
    private LinearLayout ll_car_2;
    private LinearLayout ll_car_3;
    private LinearLayout ll_car_4;
    private LinearLayout ll_car_5;

    private TextView tv_car_1;
    private TextView tv_car_2;
    private TextView tv_car_3;
    private TextView tv_car_4;
    private TextView tv_car_5;
    private TextView tv_volume;
    private TextView tv_start_money;
    private TextView tv_kg;
    private TextView tv_after;
    private View mMenuView;
    private Context mContext;

    

    private RelativeLayout rl_volume;
    private RelativeLayout rl_kg;
    private RelativeLayout rl_amount;
    private LinearLayout ll_0;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private CheckBox cb_type;
    private ImageView iv_above_gray;


    @SuppressLint("InflateParams")
    public MainPopupWindow(Context context, int select_car) {
        super(context);
        this.mContext =context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_main, null);
        this.setContentView(mMenuView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        ll_car_1 = (LinearLayout) mMenuView.findViewById(R.id.ll_car_1);
        ll_car_2 = (LinearLayout) mMenuView.findViewById(R.id.ll_car_2);
        ll_car_3 = (LinearLayout) mMenuView.findViewById(R.id.ll_car_3);
        ll_car_4 = (LinearLayout) mMenuView.findViewById(R.id.ll_car_4);
        ll_car_5 = (LinearLayout) mMenuView.findViewById(R.id.ll_car_5);

        tv_car_1 = (TextView) mMenuView.findViewById(R.id.tv_car_1);
        tv_car_2 = (TextView) mMenuView.findViewById(R.id.tv_car_2);
        tv_car_3 = (TextView) mMenuView.findViewById(R.id.tv_car_3);
        tv_car_4 = (TextView) mMenuView.findViewById(R.id.tv_car_4);
        tv_car_5 = (TextView) mMenuView.findViewById(R.id.tv_car_5);
        tv_volume = (TextView) mMenuView.findViewById(R.id.tv_volume);
        tv_start_money = (TextView) mMenuView.findViewById(R.id.tv_start_money);
        tv_kg = (TextView) mMenuView.findViewById(R.id.tv_kg);
        tv_after = (TextView) mMenuView.findViewById(R.id.tv_after);
        iv_above_gray = (ImageView) mMenuView.findViewById(R.id.iv_above_gray);

        rl_volume = (RelativeLayout) mMenuView.findViewById(R.id.rl_volume);
        rl_kg = (RelativeLayout) mMenuView.findViewById(R.id.rl_kg);
        rl_amount = (RelativeLayout) mMenuView.findViewById(R.id.rl_amount);
        ll_0 = (LinearLayout) mMenuView.findViewById(R.id.ll_0);
        ll_1 = (LinearLayout) mMenuView.findViewById(R.id.ll_1);
        ll_2 = (LinearLayout) mMenuView.findViewById(R.id.ll_2);
        cb_type = (CheckBox) mMenuView.findViewById(R.id.cb_type);

        ll_car_1.setOnClickListener(this);
        ll_car_2.setOnClickListener(this);
        ll_car_3.setOnClickListener(this);
        ll_car_4.setOnClickListener(this);
        ll_car_5.setOnClickListener(this);
        iv_above_gray.setOnClickListener(this);


        choiceCar(select_car);

        mMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_car_1:
                choiceCar(1);
                break;
            case R.id.ll_car_2:
                choiceCar(2);
                break;
            case R.id.ll_car_3:
                choiceCar(3);
                break;
            case R.id.ll_car_4:
                choiceCar(4);
                break;
            case R.id.ll_car_5:
                choiceCar(5);
                break;
            case R.id.iv_above_gray:
                dismiss();
                break;
        }

    }

    private void initColor() {
        tv_car_1.setTextColor(ContextCompat.getColor(mContext, R.color.color_gary_font));
        tv_car_2.setTextColor(ContextCompat.getColor(mContext, R.color.color_gary_font));
        tv_car_3.setTextColor(ContextCompat.getColor(mContext, R.color.color_gary_font));
        tv_car_4.setTextColor(ContextCompat.getColor(mContext, R.color.color_gary_font));
        tv_car_5.setTextColor(ContextCompat.getColor(mContext, R.color.color_gary_font));
    }

    private void choiceCar(int i) {
        initColor();
        switch (i) {
            case 1:
                tv_car_1.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
                Visiblity1();
                break;
            case 2:
                tv_car_2.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
                Visiblity();
                cb_type.setText("全拆座");
                tv_volume.setText("运输体积:2.5m³");
                tv_kg.setText("载重:500kg");
                tv_after.setText("后续:3元/Km");
                tv_start_money.setText("起步价:30元/5Km");
                break;
            case 3:
                tv_car_3.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
                Visiblity();
                tv_volume.setText("运输体积:4.5m³");
                tv_kg.setText("载重:1000kg");
                tv_after.setText("后续:4元/Km");
                tv_start_money.setText("起步价:55元/5Km");
                cb_type.setText("全拆座");
                break;
            case 4:
                tv_car_4.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
                Visiblity();
                cb_type.setText("开顶");
                tv_volume.setText("运输体积:6.5m³");
                tv_kg.setText("载重:1000kg");
                tv_after.setText("后续:5元/Km");
                tv_start_money.setText("起步价:60元/5Km");
                break;
            case 5:
                tv_car_5.setTextColor(ContextCompat.getColor(mContext, R.color.color_red));
                Visiblity();
                tv_volume.setText("运输体积:14m³");
                tv_kg.setText("载重:1800kg");
                tv_after.setText("后续:5元/Km");
                tv_start_money.setText("起步价:100元/5Km");
                cb_type.setText("开顶");
                break;
        }
    }

    private void Visiblity() {
        ll_0.setVisibility(View.GONE);
        ll_1.setVisibility(View.VISIBLE);
        ll_2.setVisibility(View.VISIBLE);
        cb_type.setVisibility(View.VISIBLE);

    }

    private void Visiblity1() {
        ll_0.setVisibility(View.VISIBLE);
        ll_1.setVisibility(View.GONE);
        ll_2.setVisibility(View.GONE);
        cb_type.setVisibility(View.GONE);
    }


}
