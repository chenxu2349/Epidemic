package com.example.epidemic.utils;

public class TimeUtil {

    public static String enlargeStart(String start) {

        // 左边左推一小时
        int sHour = Integer.parseInt(start.substring(11, 13));
        if (sHour > 1) sHour--;
        String strStartHour = sHour < 10 ? ("0" + sHour) : String.valueOf(sHour);
        String newStart = start.substring(0, 11) + strStartHour + start.substring(13);

        return newStart;
    }

    public static String enlargeEnd(String end) {

        // 右边右推一小时
        int eHour = Integer.parseInt(end.substring(11, 13));
        if (eHour < 23) eHour++;
        String strEndHour = eHour < 10 ? ("0" + eHour) : String.valueOf(eHour);
        String newEnd = end.substring(0, 11) + strEndHour + end.substring(13);

        return newEnd;
    }
}