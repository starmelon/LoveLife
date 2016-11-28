package com.starmelon.lovelife.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by starmelon on 2016/11/27 0027.
 */

public class TimeUtils {

    public static String long2String(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.format(new Timestamp(time));
    }
}
