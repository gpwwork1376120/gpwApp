package gpw.com.app.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import gpw.com.app.R;
import gpw.com.app.adapter.MyOrderAdapter;
import gpw.com.app.base.Contants;
import gpw.com.app.bean.OrderInfo;
import gpw.com.app.util.EncryptUtil;
import gpw.com.app.util.HttpUtil;
import gpw.com.app.util.LogUtil;
import gpw.com.app.util.VolleyInterface;
import gpw.com.app.view.XRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    private int status;
    private XRecyclerView rv_my_order;
    private int CurrentPage = 1;
    private ArrayList<OrderInfo> orderInfos;
    private MyOrderAdapter myOrderAdapter;

    public OrderFragment() {

    }

    public static OrderFragment newInstance(int status) {
        OrderFragment ofg = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt("status", status);
        ofg.setArguments(args);
        return ofg;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            status = args.getInt("status");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        rv_my_order = (XRecyclerView) view.findViewById(R.id.rv_my_order);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_my_order.setLayoutManager(layoutManager);
        rv_my_order.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                CurrentPage = 1;
                getOrderList(CurrentPage, 0);
            }

            @Override
            public void onLoadMore() {
                CurrentPage++;
                getOrderList(CurrentPage, 1);
            }
        });
        orderInfos = new ArrayList<>();
        myOrderAdapter = new MyOrderAdapter(getActivity(), orderInfos);
        rv_my_order.setAdapter(myOrderAdapter);
        getOrderList(1, 0);
        return view;
    }

    private void getOrderList(int PageIndex, final int ways) {
        LogUtil.i("status" + status);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId", Contants.userId);
        jsonObject.addProperty("Status", status);
        jsonObject.addProperty("PageIndex", PageIndex);
        jsonObject.addProperty("PageSize", 10);
        LogUtil.i(jsonObject.toString());
        Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
        HttpUtil.doPost(getActivity(), Contants.url_getSendOrderList, "getSendOrderList", map, new VolleyInterface(getActivity(), VolleyInterface.mListener, VolleyInterface.mErrorListener) {
            @Override
            public void onSuccess(JsonElement result) {
                LogUtil.i("result" + result.toString());

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<OrderInfo>>() {
                }.getType();

                ArrayList<OrderInfo> newOrderInfos = gson.fromJson(result, listType);

                LogUtil.i("orderInfos000" + newOrderInfos.toString());
                if (ways == 0) {
                    rv_my_order.refreshComplete("success");
                    CurrentPage = 1;
                    orderInfos.clear();
                    orderInfos.addAll(newOrderInfos);
                } else {
                    rv_my_order.loadMoreComplete();
                    if (newOrderInfos.size() < 10) {
                        rv_my_order.setNoMore(true);
                    }
                    orderInfos.addAll(newOrderInfos);
                }
                LogUtil.i("orderInfos"+ orderInfos.toString());
                myOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

                if (ways == 0) {
                    rv_my_order.refreshComplete("fail");

                } else {
                    rv_my_order.loadMoreComplete();
                }

            }

            @Override
            public void onStateError() {
                if (ways == 0) {
                    rv_my_order.refreshComplete("fail");
                } else {
                    rv_my_order.loadMoreComplete();
                }

            }
        });

    }


}
