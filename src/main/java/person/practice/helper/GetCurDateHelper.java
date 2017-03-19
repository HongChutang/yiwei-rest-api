package person.practice.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Evan Hung on 2016/12/29.
 */
public class GetCurDateHelper {
    public static String getCurDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }
}