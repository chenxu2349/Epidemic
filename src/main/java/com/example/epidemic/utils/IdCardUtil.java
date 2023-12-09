package com.example.epidemic.utils;

import java.util.Random;

public class IdCardUtil {

    static String[] province = {
            "11", "12", "13", "14", "15", "21", "22", "23", "31", "32",
            "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46",
            "50", "51", "52", "53", "54", "61", "62", "63", "64", "65"
    };

    public static String generateID() {

        Random random = new Random();
        int provinceIndex = random.nextInt(province.length);
        int cityCode = 100+ random.nextInt(555);
        int year = 1950 + random.nextInt(72);
        int month = 1 + random.nextInt(11);
        String monthString = month < 10 ? ("0" + month) : String.valueOf(month);
        int day = 1 + random.nextInt(28);
        String dayString = day < 10 ? ("0" + day) : String.valueOf(day);
        int serialNumber = random.nextInt(8999) + 1000; // 最后4位是随机数
        String idCardNumber = province[provinceIndex] + "0" + cityCode + year + monthString + dayString + serialNumber;
        return idCardNumber;

    }

    public static String generateIDByAgeAndAreaCode(int age, String areaCode) {

        Random random = new Random();
//        int provinceIndex = random.nextInt(province.length);
//        int cityCode = 100+ random.nextInt(555);
        int year = 2023 - age;
        int month = 1 + random.nextInt(11);
        String monthString = month < 10 ? ("0" + month) : String.valueOf(month);
        int day = 1 + random.nextInt(28);
        String dayString = day < 10 ? ("0" + day) : String.valueOf(day);
        int serialNumber = random.nextInt(8999) + 1000; // 最后4位是随机数
//        String idCardNumber = province[provinceIndex] + "0" + cityCode + year + monthString + dayString + serialNumber;
        String idCardNumber = areaCode + year + monthString + dayString + serialNumber;
        return idCardNumber;

    }
}
