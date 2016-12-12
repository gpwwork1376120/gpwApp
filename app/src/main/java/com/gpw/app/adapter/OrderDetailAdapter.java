package com.gpw.app.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import com.gpw.app.R;
import com.gpw.app.bean.OrderDetailInfo;

/**
 * Created by gpw on 2016/11/6.
 * --加油
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private ArrayList<OrderDetailInfo.OrderAddressBean> orderAddressBeens;
    private Context mContext;
    private OrderDetailInfo orderDetailInfo;
    private int size;


    public OrderDetailAdapter(OrderDetailInfo orderDetailInfo, Context mContext) {
        super();
        this.orderAddressBeens = (ArrayList<OrderDetailInfo.OrderAddressBean>) orderDetailInfo.getOrderAddress();
        this.mContext = mContext;
        this.orderDetailInfo = orderDetailInfo;
        this.size = orderDetailInfo.getOrderAddress().size();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        OrderDetailInfo.OrderAddressBean orderAddressBean = orderAddressBeens.get(position);
        int size = orderAddressBeens.size();
        if (position == 0) {
            if (orderAddressBean.getDischargeTime().equals("")) {
                if (!orderAddressBeens.get(1).getDischargeTime().equals("")) {
                    viewHolder.iv_state.setImageResource(R.mipmap.start_gray);
                } else {
                    viewHolder.iv_state.setImageResource(R.mipmap.start);
                }
                viewHolder.bt_location.setBackgroundResource(R.drawable.button_gray_bg);
                viewHolder.bt_location.setClickable(false);
            } else {
                viewHolder.iv_state.setImageResource(R.mipmap.start_red);
                viewHolder.bt_location.setBackgroundResource(R.drawable.button_red_bg);
                viewHolder.bt_location.setClickable(true);
                viewHolder.bt_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button) v;
                        String name = button.getText().toString();
                        mOnBtnClickListener.onBtnClick(position, name);
                    }
                });

            }
            viewHolder.tv_state.setText("到达起点，装货发车");
            viewHolder.tv_time.setText(orderAddressBean.getArriveTime());
            viewHolder.bt_confirm.setVisibility(View.GONE);
        } else if (position == size - 1) {
            if (!orderAddressBean.getDischargeTime().equals("")) {
                viewHolder.iv_state.setImageResource(R.mipmap.arrive_red);
                viewHolder.bt_location.setBackgroundResource(R.drawable.button_red_bg);
                viewHolder.bt_location.setClickable(true);
                viewHolder.bt_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button) v;
                        String name = button.getText().toString();
                        mOnBtnClickListener.onBtnClick(position, name);
                    }
                });
            } else {
                if (orderDetailInfo.getOrderStatus() == 4) {
                    viewHolder.iv_state.setImageResource(R.mipmap.arrive_gray);
                } else {
                    viewHolder.iv_state.setImageResource(R.mipmap.arrive);
                }
                viewHolder.bt_confirm.setBackgroundResource(R.drawable.button_gray_bg);
                viewHolder.bt_confirm.setClickable(false);
                viewHolder.bt_location.setBackgroundResource(R.drawable.button_gray_bg);
                viewHolder.bt_location.setClickable(false);
            }
            viewHolder.tv_state.setText("到达终点，已卸货");
            viewHolder.tv_time.setText(orderAddressBean.getArriveTime());
            viewHolder.bt_confirm.setVisibility(View.VISIBLE);
            viewHolder.bt_confirm.setText("确认收货");

        } else {
            if (orderAddressBean.getDischargeTime().equals("")) {
                if (orderAddressBeens.get(position - 1).getDischargeTime().equals("")) {
                    viewHolder.iv_state.setImageResource(R.mipmap.pass);
                } else {
                    viewHolder.iv_state.setImageResource(R.mipmap.pass_gray);
                }
                viewHolder.bt_confirm.setBackgroundResource(R.drawable.button_gray_bg);
                viewHolder.bt_confirm.setClickable(false);
                viewHolder.bt_location.setBackgroundResource(R.drawable.button_gray_bg);
                viewHolder.bt_location.setClickable(false);
            } else {
                viewHolder.iv_state.setImageResource(R.mipmap.pass_red);
                viewHolder.bt_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button) v;
                        String name = button.getText().toString();
                        mOnBtnClickListener.onBtnClick(position, name);
                    }
                });
            }
            viewHolder.tv_state.setText("到达中途点，卸货发车");
            viewHolder.tv_time.setText(orderAddressBean.getArriveTime());
            viewHolder.bt_confirm.setVisibility(View.VISIBLE);
            viewHolder.bt_confirm.setText("确认卸货");
        }
        viewHolder.tv_address.setText(String.format("%s  %s  %s", orderAddressBean.getAddress(), orderAddressBean.getReceipter(), orderAddressBean.getTel()));


        if (orderDetailInfo.getOrderStatus() == 4) {
            viewHolder.bt_confirm.setBackgroundResource(R.drawable.button_gray_bg);
            viewHolder.bt_location.setBackgroundResource(R.drawable.button_gray_bg);
            viewHolder.bt_location.setClickable(false);
            viewHolder.bt_confirm.setClickable(false);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return orderAddressBeens.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_state;
        TextView tv_time;
        TextView tv_address;
        Button bt_confirm;
        Button bt_location;
        ImageView iv_state;


        public ViewHolder(View view) {
            super(view);
            tv_state = (TextView) view.findViewById(R.id.tv_state);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            iv_state = (ImageView) view.findViewById(R.id.iv_state);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
            bt_location = (Button) view.findViewById(R.id.bt_location);

        }
    }


    public interface OnBtnClickListener {
        void onBtnClick(int position, String viewName);
    }

    private OnBtnClickListener mOnBtnClickListener;


    public void setOnBtnClickListener(OnBtnClickListener mOnBtnClickListener) {
        this.mOnBtnClickListener = mOnBtnClickListener;
    }
}
