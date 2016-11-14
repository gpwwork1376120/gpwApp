package gpw.com.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/14.
 * ---个人专属
 */

public class UserInfo implements Parcelable {

    /**
     * UserId : 9d2c8eba-f9a6-44ab-88e7-a10543afc4eb
     * UserName : 货主
     * Tel : 13530177675
     * HeadIco : aaaaa
     * Sex : 男
     * Address : 地址
     */

    private String UserId;
    private String UserName;
    private String Tel;
    private String HeadIco;
    private String Sex;
    private String Address;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String Tel) {
        this.Tel = Tel;
    }

    public String getHeadIco() {
        return HeadIco;
    }

    public void setHeadIco(String HeadIco) {
        this.HeadIco = HeadIco;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "UserId='" + UserId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Tel='" + Tel + '\'' +
                ", HeadIco='" + HeadIco + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UserId);
        dest.writeString(this.UserName);
        dest.writeString(this.Tel);
        dest.writeString(this.HeadIco);
        dest.writeString(this.Sex);
        dest.writeString(this.Address);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.UserId = in.readString();
        this.UserName = in.readString();
        this.Tel = in.readString();
        this.HeadIco = in.readString();
        this.Sex = in.readString();
        this.Address = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
