package com.kurye.kurye.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ahmet on 2/18/2018.
 */

public class DateUtils {
    public static String formatDateToString(Calendar firstDate) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        fmt.setCalendar(firstDate);
        return fmt.format(firstDate.getTime());
    }
}
