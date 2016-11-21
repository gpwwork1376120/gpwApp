package gpw.com.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import gpw.com.app.R;
import gpw.com.app.bean.ConvoyInfo;
import gpw.com.app.bean.OrderAddressInfo;

/**
 * Created by Administrator on 2016/11/21.
 * ---个人专属
 */

public class OrderAddAdapter extends BaseAdapter {
    private ArrayList<OrderAddressInfo> mOrderAddressInfos;
    private Context mContext;

    public OrderAddAdapter(ArrayList<OrderAddressInfo> mOrderAddressInfos, Context mContext) {
        super();
        this.mOrderAddressInfos = mOrderAddressInfos;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mOrderAddressInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrderAddressInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_order_address, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_state = (ImageView) convertView.findViewById(R.id.iv_state);
            viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.tv_contact = (TextView) convertView.findViewById(R.id.tv_contact);
            viewHolder.view_line = convertView.findViewById(R.id.view_line);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        OrderAddressInfo orderAddressInfo  =mOrderAddressInfos.get(position);
        if (orderAddressInfo.getState() == 1) {
            viewHolder.iv_state.setImageResource(R.mipmap.start);
        } else if (orderAddressInfo.getState() == 2) {
            viewHolder.iv_state.setImageResource(R.mipmap.pass);
        } else {
            viewHolder.iv_state.setImageResource(R.mipmap.arrive);
        }
        viewHolder.tv_address.setText(orderAddressInfo.getReceiptAddress());
        viewHolder.tv_contact.setText(String.format("%s %s", orderAddressInfo.getReceipter(), orderAddressInfo.getReceiptTel()));
        viewHolder.view_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_white));
        return convertView;
    }

    private final class ViewHolder {
        ImageView iv_state;
        TextView tv_address;
        TextView tv_contact;
        View view_line;
    }

}
