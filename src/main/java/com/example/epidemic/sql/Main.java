package com.example.epidemic.sql;

public class Main {
    public static void main(String[] args) {
        // 常量池
        String[] firstName = new String[]{"赵", "钱", "王", "孙", "李", "周", "张", "陈", "方", "陶", "江", "杨", "沈", "韩", "楚", "秦"};
        String[] lastName = new String[]{"强", "小红", "柏", "文", "子斌", "莎", "玉", "立国", "志强", "建国", "国庆", "弥", "歌", "鞍立",
                "金阳", "平", "包子", "尼尼", "维", "申堂", "树", "华清", "旭", "林木", "可欣", "柯", "胡云", "婷", "音茹", "余", "锦玉"};
        String[] areaPool = new String[]{"世贸翡翠首府", "融创城创泽园", "尚泽紫金公馆", "金水童话名苑", "锦秀大地城", "中环云邸", "世纪阳光花园紫阳苑",
                "阳光汇景", "华夏阳府", "尚泽大都会"};
        int[] patientsPool = new int[]{10001, 10002, 10003, 10004, 10005};
        String[] vacPool = new String[]{"卡介苗", "甲型肝炎疫苗", "脊髓灰质炎疫苗", "麻疹疫苗", "乙型肝炎病毒疫苗", "脊髓灰质炎减毒活疫苗",
                "乙脑减毒活疫苗", "新冠疫苗", "狂犬疫苗"};
        String[] illnessHistoryPool = new String[]{"慢性荨麻疹", "乙型肝炎", "新冠肺炎", "高血压", "糖尿病", "肺癌",
                "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无", "无"};
        String[] bloodFatPool = new String[]{"4.4/0.6/1.7/3.1", "4.5/0.8/2.0/2.5", "2.3/0.7/2.0/3.2", "2.9/0.6/1.0/3.1",
                "3.1/1.8/1.3/2.3", "4.8/1.5/1.8/2.8", "3.4/1.2/1.6/2.1", "3.3/1.4/1.4/2.5", "4.2/0.6/1.9/3.0", "3.2/1.6/1.8/2.4"};

        for (int i = 0; i < 100; i++) {
            // SQL头
            String sql = "INSERT INTO `contacts_info` VALUES (";
            int id = 20019 + i; sql += id; sql += ",";

            int seed1 = (int)(0 + Math.random()*(15 - 0 + 1)), seed2 = (int)(0 + Math.random()*(30 - 0 + 1));
            String name = firstName[seed1] + lastName[seed2]; sql += "'"; sql += name; sql += "'"; sql += ",";

            int seed3 = (int)(0 + Math.random()*(4 - 0 + 1));
            sql += patientsPool[seed3]; sql += ",";

            int seed4 = (int)(10 + Math.random()*(90 - 10 + 1));
            sql += seed4; sql += ",";

            int seed5 = (int)(0 + Math.random()*(1 - 0 + 1));
            sql += seed5; sql += ",";

            int seed6 = (int)(10000 + Math.random()*(99999 - 10000 + 1)), seed7 = (int)(10000 + Math.random()*(99999 - 10000 + 1));
            String tel = "1"; tel += seed6; tel += seed7;
            sql += "'"; sql += tel; sql += "'"; sql += ",";

            int seed8 = (int)(0 + Math.random()*(9 - 0 + 1));
            sql += "'"; sql += areaPool[seed8]; sql += "'"; sql += ",";

            int seed9 = (int)(0 + Math.random()*(8 - 0 + 1));
            sql += "'"; sql += vacPool[seed9]; sql += "'"; sql += ",";

            int seed10 = (int)(0 + Math.random()*(23 - 0 + 1));
            sql += "'"; sql += illnessHistoryPool[seed10]; sql += "'"; sql += ",";
            sql += 0; sql += ",";

            int seed11 = (int)(59 + Math.random()*(110 - 59 + 1));
            sql += "'"; sql += seed11; sql += "'"; sql += ",";

            int seed12 = (int)(95 + Math.random()*(135 - 95 + 1)), seed13 = (int)(70 + Math.random()*(94 - 70 + 1));
            String bloodPressure = ""; bloodPressure += seed12; bloodPressure += "/"; bloodPressure += seed13;
            sql += "'"; sql += bloodPressure; sql += "'"; sql += ",";

            int seed14 = (int)(2 + Math.random()*(9 - 2 + 1)), seed15 = (int)(0 + Math.random()*(9 - 0 + 1));
            String bloodGlucose = "";bloodGlucose += seed14; bloodGlucose += "."; bloodGlucose += seed15;
            sql += "'"; sql += bloodGlucose; sql += "'"; sql += ",";

            int seed16 = (int)(0 + Math.random()*(9 - 0 + 1));
            sql += "'"; sql += bloodFatPool[seed16]; sql += "'"; sql += ",";

            int seed17 = (int)(36 + Math.random()*(39 - 36 + 1)), seed18 = (int)(0 + Math.random()*(9 - 0 + 1));
            String temp = ""; temp += seed17; temp += "."; temp += seed18;
            sql += "'"; sql += temp; sql += "'"; sql += ",";

            int seed19 = (int)(105 + Math.random()*(140 - 105 + 1));
            sql += "'"; sql += seed19; sql += "'"; sql += ",";

            int seed20 = (int)(14 + Math.random()*(65 - 14 + 1));
            String feihuoliang = ""; feihuoliang += seed20; feihuoliang += "00";
            sql += "'"; sql += feihuoliang; sql += "'";

            // SQL尾
            sql += ");";
            System.out.println(sql);
        }
    }
}