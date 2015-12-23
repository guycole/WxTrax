package com.digiburo.wxtraxlib.utility;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    /**
     * Return time
     * @return return current time
     */
    public static Time timeNow() {
        Time time = new Time();
        time.setToNow();
        return time;
    }

    /**
     * convert a rfc822 time to object
     * @param arg rfc822 formatted time
     * @return time
     */
    public static Time stringToTime(String arg) {
        Date date = null;

        //Sat, 18 Jun 2011 09:53:00 -0700
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        try {
            date = sdf.parse(arg);
        } catch(Exception exception) {
            return null;
        }

        Time time = new Time();
        time.set(date.getTime());

        return time;
    }

    /**
     * pick argument or default if argument null
     *
     * @param arg candidate
     * @param defArg default
     * @return candidate if not null else default
     */
    public static String eclecticString(String arg, String defArg) {
        if ((arg != null) && (arg.trim() != null)) {
            return arg.trim();
        } else {
            return defArg;
        }
    }
}
