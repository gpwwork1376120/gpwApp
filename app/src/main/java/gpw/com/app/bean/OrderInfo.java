package gpw.com.app.bean;

import com.google.gson.JsonElement;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22.
 * ---个人专属
 */

public class OrderInfo {


    /**
     * OrderNo : TO20161122000006
     * PlanSendTime : 2016/11/25 14:03:00
     * Freight : 221.4
     * OrderStatus : 1
     * FinanceStatus : 1
     * LogisticStatus : 0
     * OrderType : 2
     * CancelFee : 0.0
     * OrderAddress : [{"Address":"pepperclub  (广东省深圳市福田区民田路138号)","Receipter":"fff","Tel":"88888"},{"Address":"深圳百度国际大厦  (广东省深圳市南山区学府路)","Receipter":"55","Tel":"58"}]
     */

    private String OrderNo;
    private String PlanSendTime;
    private double Freight;
    private int OrderStatus;
    private int FinanceStatus;
    private int LogisticStatus;
    private int OrderType;
    private double CancelFee;
    /**
     * Address : pepperclub  (广东省深圳市福田区民田路138号)
     * Receipter : fff
     * Tel : 88888
     */

    private JsonElement OrderAddress;

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPlanSendTime() {
        return PlanSendTime;
    }

    public void setPlanSendTime(String PlanSendTime) {
        this.PlanSendTime = PlanSendTime;
    }

    public double getFreight() {
        return Freight;
    }

    public void setFreight(double Freight) {
        this.Freight = Freight;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public int getFinanceStatus() {
        return FinanceStatus;
    }

    public void setFinanceStatus(int FinanceStatus) {
        this.FinanceStatus = FinanceStatus;
    }

    public int getLogisticStatus() {
        return LogisticStatus;
    }

    public void setLogisticStatus(int LogisticStatus) {
        this.LogisticStatus = LogisticStatus;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int OrderType) {
        this.OrderType = OrderType;
    }

    public double getCancelFee() {
        return CancelFee;
    }

    public void setCancelFee(double CancelFee) {
        this.CancelFee = CancelFee;
    }

    public JsonElement getJsonElement() {
        return OrderAddress;
    }

    public void setJsonElement(JsonElement jsonElement) {
        this.OrderAddress = jsonElement;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "OrderNo='" + OrderNo + '\'' +
                ", PlanSendTime='" + PlanSendTime + '\'' +
                ", Freight=" + Freight +
                ", OrderStatus=" + OrderStatus +
                ", FinanceStatus=" + FinanceStatus +
                ", LogisticStatus=" + LogisticStatus +
                ", OrderType=" + OrderType +
                ", CancelFee=" + CancelFee +
                ", JsonElement=" + OrderAddress.toString() +
                '}';
    }
}
