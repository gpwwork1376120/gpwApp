package gpw.com.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gpw on 2016/8/11.
 * --加油
 */
public class DateUtil {

    public static String getPsdCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(d);
    }

    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }
    public static String getDate(Date d) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

}