package gpw.com.app.bean;

public class OrderAddressBean {
    private String Address;
    private String Receipter;
    private String Tel;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getReceipter() {
        return Receipter;
    }

    public void setReceipter(String Receipter) {
        this.Receipter = Receipter;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    @Override
    public String toString() {
        return "OrderAddressBean{" +
                "Address='" + Address + '\'' +
                ", Receipter='" + Receipter + '\'' +
                ", Tel='" + Tel + '\'' +
                '}';
    }
}