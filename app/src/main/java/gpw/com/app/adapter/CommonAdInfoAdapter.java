package gpw.com.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import gpw.com.app.bean.CommonAdInfo;

import static android.view.View.GONE;

/**
 * Created by gpw on 2016/11/5.
 * --加油
 */

public class CommonAdInfoAdapter extends RecyclerView.Adapter<CommonAdInfoAdapter.ViewHolder> {

    private ArrayList<CommonAdInfo> commonAdInfos;

    private Context context;


    public CommonAdInfoAdapter(Context context,ArrayList<CommonAdInfo> commonAdInfos) {
        super();
        this.commonAdInfos = commonAdInfos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_common_address, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final CommonAdInfo commonAdInfo = commonAdInfos.get(position);
        viewHolder.tv_address.setText(commonAdInfo.getReceiptAddress());
        viewHolder.tv_contact.setText(commonAdInfo.getReceipter()+" "+commonAdInfo.getReceiptTel());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return commonAdInfos.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_address;
        TextView tv_contact;

        public ViewHolder(View view) {
            super(view);
            tv_address = (TextView) view.findViewById(R.id.tv_address);
            tv_contact = (TextView) view.findViewById(R.id.tv_contact);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
