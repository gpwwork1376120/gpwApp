package gpw.com.app.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by gpw on 2016/11/5.
 * --加油
 */

public class AddressMainInfo implements Parcelable {

    private int state;
    private int action;
    private boolean start;
    private String contact;
    private String address;
    private String name;
    private LatLng mLatLng;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AddressMainInfo{" +
                "action=" + action +
                ", state=" + state +
                ", start=" + start +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", mLatLng=" + mLatLng +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.state);
        dest.writeInt(this.action);
        dest.writeByte(this.start ? (byte) 1 : (byte) 0);
        dest.writeString(this.contact);
        dest.writeString(this.address);
        dest.writeString(this.name);
        dest.writeParcelable(this.mLatLng, flags);
    }

    public AddressMainInfo() {
    }

    protected AddressMainInfo(Parcel in) {
        this.state = in.readInt();
        this.action = in.readInt();
        this.start = in.readByte() != 0;
        this.contact = in.readString();
        this.address = in.readString();
        this.name = in.readString();
        this.mLatLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<AddressMainInfo> CREATOR = new Creator<AddressMainInfo>() {
        @Override
        public AddressMainInfo createFromParcel(Parcel source) {
            return new AddressMainInfo(source);
        }

        @Override
        public AddressMainInfo[] newArray(int size) {
            return new AddressMainInfo[size];
        }
    };
}
