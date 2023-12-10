package com.example.epidemic;

import com.example.epidemic.mapper.DataPrepareMapper;
import com.example.epidemic.pojo.Contact;
import com.example.epidemic.utils.ExportUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
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

    @Autowired
    private DataPrepareMapper dataPrepareMapper;

    @Test
    public void exportCsvTest() throws IOException {

        List<Contact> allContact = dataPrepareMapper.getAllContact();

        // 想要导出的位置
        String filePath = "src/main/resources/exportFiles/data.csv";
        // 表头
        String header = "id,name,area_code,id_num,tel,probability,tag,date";

        ArrayList<String> data = new ArrayList<>();
        data.add(header);

        for (Contact c : allContact) {
            if (c.getPotentialPatient() != 1) continue;
            String item = "";
            int id = c.getContactId();
            String name = c.getContactName();
            String areaCode = c.getAreaCode();
            String idNum = c.getContactIdentity();
            String tel = c.getContactTel();
            double probability = c.getPotentialPatientProbability();
            int tag = probability > 0.75 ? 1 : 0;
            String date = c.getContactDate();
            item = "" + id + "," + name + "," + areaCode + "," + idNum + "," + tel + "," + probability +
                    "," +tag + "," + date;
            data.add(item);
        }

        ExportUtils.exportCsv(data, filePath);
    }
}
