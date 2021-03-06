package cn.com.goodsowner.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import cn.com.goodsowner.base.BaseActivity;
import cn.com.goodsowner.base.Contants;
import cn.com.goodsowner.util.EncryptUtil;
import cn.com.goodsowner.util.HttpUtil;
import cn.com.goodsowner.util.LogUtil;
import cn.com.goodsowner.util.VolleyInterface;

public class ApplyInvoiceActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_right;
    private ImageView iv_left_white;

    private TextView tv_invoiceAmount;
    private EditText et_company;
    private EditText et_address;
    private EditText et_contact_name;
    private EditText et_contact_tel;
    private Button bt_apply;
    private String orders;
    private double invoiceAmount;


    @Override
    protected int getLayout() {
        return cn.com.goodsowner.R.layout.activity_apply_invoice;
    }

    @Override
    protected void findById() {
        RelativeLayout rl_head = (RelativeLayout) findViewById(cn.com.goodsowner.R.id.in_head);
        assert rl_head != null;
        tv_title = (TextView) rl_head.findViewById(cn.com.goodsowner.R.id.tv_title);
        tv_right = (TextView) rl_head.findViewById(cn.com.goodsowner.R.id.tv_right);
        iv_left_white = (ImageView) rl_head.findViewById(cn.com.goodsowner.R.id.iv_left_white);
        tv_invoiceAmount = (TextView) findViewById(cn.com.goodsowner.R.id.tv_invoiceAmount);
        et_company = (EditText) findViewById(cn.com.goodsowner.R.id.et_company);
        et_address = (EditText) findViewById(cn.com.goodsowner.R.id.et_address);
        et_contact_name = (EditText) findViewById(cn.com.goodsowner.R.id.et_contact_name);
        et_contact_tel = (EditText) findViewById(cn.com.goodsowner.R.id.et_contact_tel);
        bt_apply = (Button) findViewById(cn.com.goodsowner.R.id.bt_apply);
    }

    @Override
    protected void initData() {
        orders = getIntent().getStringExtra("orders");
        invoiceAmount = getIntent().getDoubleExtra("money",0);
    }

    @Override
    protected void initView() {
        tv_title.setText(cn.com.goodsowner.R.string.apply_invoice);
        tv_invoiceAmount.setText(String.format("¥%s元", invoiceAmount));
        tv_right.setVisibility(View.GONE);
        iv_left_white.setOnClickListener(this);
        bt_apply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case cn.com.goodsowner.R.id.iv_left_white:
                finish();
                break;
            case cn.com.goodsowner.R.id.bt_apply:
                String company = et_company.getText().toString();
                String address = et_address.getText().toString();
                String contacts = et_contact_name.getText().toString();
                String contactTel = et_contact_tel.getText().toString();
                if (company.isEmpty()||address.isEmpty()|contacts.isEmpty()||contactTel.isEmpty()){
                    showShortToastByString("信息不完整");
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("UserId", Contants.userId);
                jsonObject.addProperty("Orders", orders);
                jsonObject.addProperty("InvoiceAmount", invoiceAmount);
                jsonObject.addProperty("Company", company);
                jsonObject.addProperty("Address", address);
                jsonObject.addProperty("Contacts", contacts);
                jsonObject.addProperty("ContactTel", contactTel);
                Map<String, String> map = EncryptUtil.encryptDES(jsonObject.toString());
                HttpUtil.doPost(ApplyInvoiceActivity.this, Contants.url_applayInvoice, "applayInvoic", map, new VolleyInterface(ApplyInvoiceActivity.this, VolleyInterface.mListener, VolleyInterface.mErrorListener) {
                    @Override
                    public void onSuccess(JsonElement result) {
                        LogUtil.i(result.toString());
                        showShortToastByString(result.toString());
                        finish();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        showShortToastByString(getString(cn.com.goodsowner.R.string.timeoutError));
//                LogUtil.i("hint",error.networkResponse.headers.toString());
//                LogUtil.i("hint",error.networkResponse.statusCode+"");
                    }

                    @Override
                    public void onStateError() {
                    }
                });
                break;

        }
    }
}
