package gpw.com.app.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gpw.com.app.R;
import gpw.com.app.activity.MapActivity;
import gpw.com.app.bean.AddressMainInfo;
import gpw.com.app.util.LogUtil;

import static android.view.View.GONE;

/**
 * Created by gpw on 2016/11/5.
 * --加油
 */

public class AddressMainAdapter extends RecyclerView.Adapter<AddressMainAdapter.ViewHolder> {

    private ArrayList<AddressMainInfo> mAddressMainInfos;

    private Activity mActivity;


    public AddressMainAdapter(ArrayList<AddressMainInfo> mAddressMainInfos, Activity activity) {
        super();
        this.mAddressMainInfos = mAddressMainInfos;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_address_main_info, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final AddressMainInfo addressMainInfo = mAddressMainInfos.get(position);
        LogUtil.i("hint","aa"+addressMainInfo.getAddress());
        if (addressMainInfo.getAddress().equals("start")) {
            viewHolder.tv_contact.setVisibility(GONE);
            viewHolder.tv_address.setText("点击添加地址");
//            viewHolder.tv_address.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mActivity, MapActivity.class);
//                    intent.putExtra("position",position);
//                    intent.putExtra("addressMainInfo",addressMainInfo);
//                    intent.putExtra("type",1);
//                    mActivity.startActivityForResult(intent,1);
//                }
//            });
        } else {
            viewHolder.tv_contact.setVisibility(View.VISIBLE);
            viewHolder.tv_address.setText(addressMainInfo.getName()+"("+addressMainInfo.getAddress()+")");
            viewHolder.tv_contact.setText(addressMainInfo.getContact());
        }

        viewHolder.ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });


        if (addressMainInfo.getState() == 1) {
            viewHolder.iv_state.setImageResource(R.mipmap.start);
            viewHolder.view_line.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_line));
        } else if (addressMainInfo.getState() == 2) {
            viewHolder.iv_state.setImageResource(R.mipmap.pass);
            viewHolder.view_line.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_line));
        } else {
            viewHolder.iv_state.setImageResource(R.mipmap.arrive);
            viewHolder.view_line.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.color_white));
        }


        if (addressMainInfo.getAction() == 1) {
            viewHolder.iv_action.setImageResource(R.mipmap.add);
            viewHolder.iv_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, MapActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("addressMainInfo",addressMainInfo);
                    intent.putExtra("type",2);
                    mActivity.startActivityForResult(intent,1);
                }
            });
        } else if (addressMainInfo.getState() == 2) {
            viewHolder.iv_action.setImageResource(R.mipmap.close);
            viewHolder.iv_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onActionClick(position);
                }
            });
        } else {
            viewHolder.iv_action.setImageResource(0);
        }



    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mAddressMainInfos.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_state;
        ImageView iv_action;
        TextView tv_address;
        TextView tv_contact;
        View view_line;
        LinearLayout ll_address;


        public ViewHolder(View view) {
            super(view);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_contact = (TextView) view.findViewById(R.id.tv_contact);
            iv_state = (ImageView) view.findViewById(R.id.iv_state);
            iv_action = (ImageView) view.findViewById(R.id.iv_action);
            view_line = view.findViewById(R.id.view_line);
            ll_address = (LinearLayout) view.findViewById(R.id.ll_address);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onActionClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
