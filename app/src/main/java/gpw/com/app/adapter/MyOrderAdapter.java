package gpw.com.app.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import gpw.com.app.R;
import gpw.com.app.bean.InvoiceInfo;
import gpw.com.app.bean.OrderAddressBean;
import gpw.com.app.bean.OrderInfo;
import gpw.com.app.util.LogUtil;

/**
 * Created by gpw on 2016/11/5.
 * --加油
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private ArrayList<OrderInfo> orderInfos;

    private Context context;


    public MyOrderAdapter(Context context, ArrayList<OrderInfo> orderInfos) {
        super();
        this.orderInfos = orderInfos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_order, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final OrderInfo orderInfo = orderInfos.get(position);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<OrderAddressBean>>() {
        }.getType();
        ArrayList<OrderAddressBean> orderAddressBeen = gson.fromJson(orderInfo.getJsonElement(), listType);

        LogUtil.i(orderAddressBeen.toString());
        OrderAddInfoAdapter addInfoAdapter = new OrderAddInfoAdapter(orderAddressBeen,context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.rv_order_address.setLayoutManager(layoutManager);
        viewHolder.rv_order_address.setAdapter(addInfoAdapter);
        viewHolder.tv_money.setText(String.format("¥%s", orderInfo.getFreight()));
        viewHolder.tv_orderId.setText(String.format("订单号：%s", orderInfo.getOrderNo()));
        viewHolder.tv_time.setText(orderInfo.getPlanSendTime());

        switch (orderInfo.getOrderStatus()){
            case 1:
                if(orderInfo.getOrderType()==3){
                    viewHolder.bt_query.setText("查看报价");
                    viewHolder.tv_state.setText("询价进行中");
                    viewHolder.tv_state.setVisibility(View.VISIBLE);
                    viewHolder.tv_noDriver.setVisibility(View.GONE);
                    viewHolder.tv_addMoney.setVisibility(View.GONE);
                    viewHolder.tv_money.setVisibility(View.GONE);

                }else {
                    viewHolder.tv_money.setVisibility(View.VISIBLE);
                    viewHolder.tv_state.setVisibility(View.GONE);
                    viewHolder.tv_noDriver.setVisibility(View.VISIBLE);
                    viewHolder.tv_addMoney.setVisibility(View.VISIBLE);
                    viewHolder.bt_query.setText("取消订单");
                }
                break;
            case 2:
                viewHolder.tv_state.setText("进行中");
                viewHolder.bt_query.setText("查看详情");
                break;
            case 3:
                viewHolder.tv_state.setText("已送达");
                viewHolder.bt_query.setText("查看详情");
                break;
            case 4:
                viewHolder.tv_state.setText("已完成");
                break;
            case -1:
                viewHolder.tv_state.setText("已取消");
                viewHolder.bt_query.setVisibility(View.GONE);
                break;
        }
        viewHolder.bt_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mOnBtnClickListener.onBtnClick(position);
            }
        });

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return orderInfos.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button bt_query;
        TextView tv_money;
        TextView tv_time;
        TextView tv_orderId;
        TextView tv_state;
        TextView tv_noDriver;
        TextView tv_addMoney;
        RecyclerView rv_order_address;

        public ViewHolder(View view) {
            super(view);
            bt_query = (Button) view.findViewById(R.id.bt_query);
            tv_money = (TextView) view.findViewById(R.id.tv_money);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_orderId = (TextView) view.findViewById(R.id.tv_orderId);
            rv_order_address = (RecyclerView) view.findViewById(R.id.rv_order_address);
            tv_state = (TextView) view.findViewById(R.id.tv_state);
            tv_noDriver = (TextView) view.findViewById(R.id.tv_noDriver);
            tv_addMoney = (TextView) view.findViewById(R.id.tv_addMoney);
        }
    }

    public interface OnBtnClickListener {
        void onBtnClick(int position);
    }

    private OnBtnClickListener mOnBtnClickListener;


    public void setOnBtnClickListener(OnBtnClickListener mOnBtnClickListener) {
        this.mOnBtnClickListener = mOnBtnClickListener;
    }
}
