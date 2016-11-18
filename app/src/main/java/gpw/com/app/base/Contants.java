package gpw.com.app.base;

/**
 * Created by gpw on 2016/10/12.
 * --加油
 */

public class Contants {
    public static String userId;
    public final static String SHARED_NAME = "gpwsp";
    public static String imagehost = "http://121.40.212.89:8009";
    private static String localhost = "http://121.40.212.89:8009/";
    public static String url_register = localhost + "Register/Register";
    public static String url_obtainCheckCode = localhost + "Visitors/ObtainCheckCode";
    public static String url_userLogin = localhost + "Register/UserLogin";
    public static String url_getUserAddress = localhost + "Consignor/GetUserAddress";
    public static String url_getUserVehicleTeam = localhost + "Consignor/GetUserVehicleTeam";
    public static String url_saveUserAddress = localhost + "Consignor/SaveUserAddress";
    public static String url_editUserAddress = localhost + "Consignor/EditUserAddress";
    public static String url_saveSuggest = localhost + "Users/SaveSuggest";
    public static String url_editUserInfo = localhost + "Users/EditUserInfo";
    public static String url_updateHeadPortrait = localhost + "Consignor/UpdateHeadPortrait";
    public static String url_getAdvertisings = localhost + "Visitors/GetAdvertisings";
    public static String url_getVehicleTypes = localhost + "Visitors/GetVehicleTypes";
    public static String url_getNotInvoiceList = localhost + "Consignor/GetNotInvoiceList";
    public static String url_applayInvoice = localhost + "Consignor/ApplayInvoice";
    public static String url_getUserMessages = localhost + "Users/GetUserMessages";
    public static String url_obtainMessage = localhost + "Visitors/ObtainMessage";
    public static String url_getUserBalanceList = localhost + "Finance/GetUserBalanceList";
    public static String url_getUserBalance = localhost + "Finance/GetUserBalance";

}
