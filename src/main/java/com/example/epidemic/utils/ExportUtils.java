package com.example.epidemic.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExportUtils
 * @Description
 * @Author chenxu
 * @Date 2023/12/1 13:12
 **/
public class ExportUtils {

    public static void exportCsv(ArrayList<String> data, String filePath) {

        try {
            FileWriter fileWriter = new FileWriter(filePath);

            // 写入表头
            fileWriter.append(data.get(0));
            fileWriter.append("\n");

            // 写入数据
            for (int i = 1; i < data.size(); i++) {
                fileWriter.append(data.get(i));
                fileWriter.append("\n");
            }

            fileWriter.flush();
            fileWriter.close();
            System.out.println("数据成功导出到" + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
