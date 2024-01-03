package com.example.epidemic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Normalization {

    // 数据归一化为规定区间概率
    public static double normalization(int point, int factors) {
        int max = factors * 100;
        int seed = RandomGenerator.getRandomInt(0, 2);
        if (point >= max) {
            if (seed == 0) return 0.97;
            else if (seed == 1) return 0.96;
            else return 0.95;
        }
        else return point * 1.0 / (factors * 100);
    }

    // 接触时间
    public static int contactTime(String p_start, String p_end, String c_start, String c_end) throws ParseException {

        // String类型转为日期类型比较
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 时间戳，毫秒
        long ps = format.parse(p_start).getTime();
        long pe = format.parse(p_end).getTime();
        long cs = format.parse(c_start).getTime();
        long ce = format.parse(c_end).getTime();

        long count = 0;
        if (cs >= ps && cs <= pe) {
            if (ce >= pe) count = pe - cs;
            else count = ce - cs;
        }
        if (ps >= cs && ps <= ce) {
            if (pe >= ce) count = ce - ps;
            else count = pe - ps;
        }

        // ms -> min 两个时间段交集是多少分钟
        return (int)(count/60000);
    }

    // 佩戴口罩情况, 0-10代表感染风险
    public static int maskSituation(int a, int b) {
        if (a == 1 && b == 1) return 1;
        else if (a == 1 || b == 1) return 3;
        else return 9;
    }
}
