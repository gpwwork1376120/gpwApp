package gpw.com.app.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gpw.com.app.R;
import gpw.com.app.activity.ConfirmOrderActivity;
import gpw.com.app.bean.OrderDetailInfo;
import gpw.com.app.bean.OrderOfferInfo;

/**
 * Created by gpw on 2016/11/6.
 * --加油
 */

public class OrderDetailAdapter extends BaseAdapter {
    private ArrayList<OrderDetailInfo.OrderAddressBean> orderAddressBeens;
    private OrderDetailInfo.OrderAddressBean orderAddressBean;
    private OrderDetailInfo orderDetailInfo;
    private Context mContext;
    private int size;

    public OrderDetailAdapter(OrderDetailInfo orderDetailInfo, Context mContext) {
        super();
        this.orderAddressBeens = (ArrayList<OrderDetailInfo.OrderAddressBean>) orderDetailInfo.getOrderAddress();
        this.orderDetailInfo = orderDetailInfo;
        this.mContext = mContext;
        this.size = orderAddressBeens.size() + 2;
    }

    @Override
    public int getCount() {
        return orderAddressBeens.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return orderAddressBeens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_order_detail, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_name1 = (TextView) convertView.findViewById(R.id.tv_name1);
            viewHolder.tv_vehicleNo = (TextView) convertView.findViewById(R.id.tv_vehicleNo);
            viewHolder.tv_vehicleNo1 = (TextView) convertView.findViewById(R.id.tv_vehicleNo1);
            viewHolder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            viewHolder.tv_startState = (TextView) convertView.findViewById(R.id.tv_startState);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.type_2 = (TextView) convertView.findViewById(R.id.type_2);
            viewHolder.type_1 = (LinearLayout) convertView.findViewById(R.id.type_1);
            viewHolder.type_3 = (LinearLayout) convertView.findViewById(R.id.type_3);
            viewHolder.ll_carInfo = (LinearLayout) convertView.findViewById(R.id.ll_carInfo);
            viewHolder.bt_1 = (Button) convertView.findViewById(R.id.bt_1);
            viewHolder.bt_2 = (Button) convertView.findViewById(R.id.bt_2);
            viewHolder.rb_score = (RatingBar) convertView.findViewById(R.id.rb_score1);
            viewHolder.rb_score1 = (RatingBar) convertView.findViewById(R.id.rb_score);
            viewHolder.iv_state = (ImageView) convertView.findViewById(R.id.iv_state);
            viewHolder.rl_line = (RelativeLayout) convertView.findViewById(R.id.rl_line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position > 0 && position < size - 1) {
            orderAddressBean = orderAddressBeens.get(position - 1);
        }


        if (position == 0) {
            viewHolder.tv_state.setText("车主已接单，等待发货");
            viewHolder.tv_time.setText(orderDetailInfo.getGrabTime());
            viewHolder.tv_name.setText(orderDetailInfo.getTransporterName());
            viewHolder.tv_vehicleNo.setText(orderDetailInfo.getVehicleNo());
            viewHolder.rb_score.setProgress(orderDetailInfo.getTransporterScore());
            viewHolder.tv_money.setText(String.format("¥ %s", orderDetailInfo.getFreight()));
            String startState = orderDetailInfo.getVehicleTypeName();
            if (orderDetailInfo.getRemove().equals("True")) {
                if (startState.equals("小型货车") || startState.equals("中型货车")) {
                    startState = startState + " 开顶";
                } else if (startState.equals("小面包车") || startState.equals("中面包车")) {
                    startState = startState + "  全拆座";
                } else {
                    startState = startState + orderDetailInfo.getVolume() + "km  " + orderDetailInfo.getWeight() + "kg";
                }
            }
            if (orderDetailInfo.getMove().equals("True")) {
                startState = startState + "  搬运";
            }
            if (orderDetailInfo.getIsToPay().equals("True")) {
                startState = startState + "  货到付款";
            }
            viewHolder.tv_startState.setText(startState);
            viewHolder.bt_1.setText("呼叫车主");
            viewHolder.bt_2.setText("取消订单");
        } else if (position == size - 1) {
            viewHolder.iv_state.setImageResource(R.mipmap.comment_red);
            viewHolder.rl_line.setVisibility(View.GONE);
            viewHolder.tv_state.setText("您的评价，让我们做的更好");
            viewHolder.type_3.setVisibility(View.VISIBLE);
            viewHolder.type_1.setVisibility(View.GONE);
            viewHolder.type_2.setVisibility(View.GONE);
            viewHolder.ll_carInfo.setVisibility(View.GONE);
            viewHolder.bt_1.setVisibility(View.GONE);
            viewHolder.bt_2.setText("收藏至车队");
            viewHolder.bt_2.setBackground(ContextCompat.getDrawable(mContext, R.drawable.button_red_bg));
            viewHolder.tv_name1.setText(orderDetailInfo.getTransporterName());
            viewHolder.tv_vehicleNo1.setText(orderDetailInfo.getVehicleNo());
            viewHolder.rb_score.setProgress(orderDetailInfo.getTransporterScore());
        } else if (position == 1) {
            viewHolder.iv_state.setImageResource(R.mipmap.start);
            viewHolder.tv_state.setText("到达起点，装货发车");
            viewHolder.ll_carInfo.setVisibility(View.GONE);
            viewHolder.type_3.setVisibility(View.GONE);
            viewHolder.type_1.setVisibility(View.GONE);
            viewHolder.type_2.setVisibility(View.VISIBLE);
            viewHolder.bt_2.setBackground(ContextCompat.getDrawable(mContext, R.drawable.button_gray_bg));
            viewHolder.bt_1.setVisibility(View.GONE);
            viewHolder.bt_2.setText("车辆定位");
            viewHolder.type_2.setText(orderAddressBean.getAddress() + " " + orderAddressBean.getReceipter() + " " + orderAddressBean.getTel());
        } else {
            if (orderAddressBeens.size() == 2) {
                viewHolder.iv_state.setImageResource(R.mipmap.arrive);
                viewHolder.tv_state.setText("到达终点，已收货");
                viewHolder.ll_carInfo.setVisibility(View.GONE);
                viewHolder.type_3.setVisibility(View.GONE);
                viewHolder.type_1.setVisibility(View.GONE);
                viewHolder.type_2.setVisibility(View.VISIBLE);
                viewHolder.bt_1.setVisibility(View.VISIBLE);
                viewHolder.bt_2.setVisibility(View.VISIBLE);
                viewHolder.bt_2.setText("车辆定位");
                viewHolder.bt_1.setText("确认收货");
                viewHolder.tv_time.setText(orderAddressBean.getArriveTime());
                viewHolder.type_2.setText(orderAddressBean.getAddress() + " " + orderAddressBean.getReceipter() + " " + orderAddressBean.getTel());
            } else if (orderAddressBeens.size() > 2) {
                if (position == size - 2) {
                    viewHolder.iv_state.setImageResource(R.mipmap.arrive);
                    viewHolder.tv_state.setText("到达终点，已收货");
                    viewHolder.ll_carInfo.setVisibility(View.GONE);
                    viewHolder.type_3.setVisibility(View.GONE);
                    viewHolder.type_1.setVisibility(View.GONE);
                    viewHolder.type_2.setVisibility(View.VISIBLE);
                    viewHolder.bt_1.setVisibility(View.VISIBLE);
                    viewHolder.bt_2.setVisibility(View.VISIBLE);
                    viewHolder.bt_2.setText("车辆定位");
                    viewHolder.bt_1.setText("确认收货");
                    viewHolder.tv_time.setText(orderAddressBean.getArriveTime());
                    viewHolder.type_2.setText(orderAddressBean.getAddress() + " " + orderAddressBean.getReceipter() + " " + orderAddressBean.getTel());
                } else {
                    viewHolder.iv_state.setImageResource(R.mipmap.pass);
                    viewHolder.tv_state.setText("到达中途点，已卸货");
                    viewHolder.ll_carInfo.setVisibility(View.GONE);
                    viewHolder.type_3.setVisibility(View.GONE);
                    viewHolder.type_1.setVisibility(View.GONE);
                    viewHolder.type_2.setVisibility(View.VISIBLE);
                    viewHolder.bt_1.setVisibility(View.VISIBLE);
                    viewHolder.bt_2.setVisibility(View.VISIBLE);
                    viewHolder.bt_2.setText("车辆定位");
                    viewHolder.bt_1.setText("确认卸货");
                    viewHolder.tv_time.setText(orderAddressBean.getArriveTime());
                    viewHolder.type_2.setText(orderAddressBean.getAddress() + " " + orderAddressBean.getReceipter() + " " + orderAddressBean.getTel());
                }
            }
        }

        viewHolder.bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String name = button.getText().toString();
                mOnBtnClickListener.onBtnClick(position,name);
            }
        });
        viewHolder.bt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String name = button.getText().toString();
                mOnBtnClickListener.onBtnClick(position,name);
            }
        });

        return convertView;
    }




    private final class ViewHolder {
        TextView tv_money;
        TextView tv_name;
        TextView tv_name1;
        TextView tv_state;
        RatingBar rb_score;
        RatingBar rb_score1;
        TextView tv_vehicleNo;
        TextView tv_vehicleNo1;
        TextView tv_startState;
        TextView tv_time;
        LinearLayout type_1;
        LinearLayout type_3;
        LinearLayout ll_carInfo;
        TextView type_2;
        Button bt_1;
        Button bt_2;
        ImageView iv_state;
        RelativeLayout rl_line;
    }


    public interface OnBtnClickListener {
        void onBtnClick(int position,String viewName);
    }

    private OnBtnClickListener mOnBtnClickListener;


    public void setOnBtnClickListener(OnBtnClickListener mOnBtnClickListener) {
        this.mOnBtnClickListener = mOnBtnClickListener;
    }
}
