package com.example.epidemic;

import com.example.epidemic.utils.ExportUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExportFileTest
 * @Description
 * @Author chenxu
 * @Date 2023/12/1 13:33
 **/
@SpringBootTest
public class ExportFileTest {

    @Test
    public void exportCsvTest() {

        // 想要导出的位置
        String filePath = "src/main/resources/exportFiles/data.csv";
        // 表头
        String header = "id,name,age";

        ArrayList<String> data = new ArrayList<>();
        data.add(header);
        ExportUtils.exportCsv(data, filePath);
    }
}
