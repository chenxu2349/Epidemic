package com.example.epidemic;

import com.example.epidemic.mapper.DataPrepareMapper;
import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.ContactTrack;
import com.example.epidemic.pojo.Patient;
import com.example.epidemic.pojo.PatientTrack;
import com.example.epidemic.service.DataPrepareService;
import com.example.epidemic.utils.IdCardUtil;
import com.example.epidemic.utils.ThreadPoolFactory;
import com.example.epidemic.utils.TimeUtil;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName DataPrepareTest
 * @Description
 * @Author chenxu
 * @Date 2023/12/2 22:58
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class DataPrepareTest {

    @Autowired
    private DataPrepareService dataPrepareService;

    @Autowired
    private DataPrepareMapper dataPrepareMapper;

//    @Test
//    public void check() throws ParseException, InterruptedException {
//        // 全部区域编码
//        String[] areaCodePool = new String[]{"101010", "101011", "101012", "101013",
//                "101014", "111010", "121010", "121011", "121012", "121013"};
//
//        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();
//        for (String areaCode : areaCodePool) {
//            System.out.println(areaCode);
//            threadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("thread" + Math.random() + "has started...");
//                    try {
//                        dataPrepareService.check(areaCode);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        threadPool.shutdown();
//    }

    @Test
    public void check() throws ParseException, InterruptedException {
        // 全部区域编码
        String[] areaCodePool = new String[]{"101010", "101011", "101012", "101013",
                "101014", "111010", "121010", "121011", "121012", "121013"};

        for (String areaCode : areaCodePool) {
            try {
                dataPrepareService.check(areaCode);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void enlargeTimeSpanTest() throws InterruptedException {
        dataPrepareService.enlargeTrackTimeSpan();
        System.out.println("All Computation complete...");
    }

    @Test
    public void test1() {
        String s1 = "2023-07-01 12:00:00";
        String e1 = "2023-07-01 19:00:00";
        String s2 = TimeUtil.enlargeStart(s1);
        String e2 = TimeUtil.enlargeEnd(e1);
        dataPrepareMapper.setCtiTimeById(1, s2, e2);
    }

    // 给患者和接触者加上身份证号
    @Test
    public void setIdentity() throws InterruptedException {

        List<Patient> allPatient = dataPrepareMapper.getAllPatient();
        List<Contact> allContact = dataPrepareMapper.getAllContact();

        List<List<Patient>> pLists = ListUtils.partition(allPatient, 500);
        List<List<Contact>> cLists = ListUtils.partition(allContact, 1000);

        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();
        List<Thread> threads = new ArrayList<>();

        for (List<Patient> list : pLists) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("sub patient task start...");
                    for (Patient p : list) {
                        int id = p.getPatientId();
                        int age = p.getPatientAge();
                        String areaCode = p.getAreaCode();
                        dataPrepareMapper.setPatientIdentity(id, IdCardUtil.generateIDByAgeAndAreaCode(age, areaCode));
                    }
                    System.out.println("sub patient task over...");
                }
            });
            Thread.sleep(500);
        }

        for (List<Contact> list : cLists) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("sub contact task start...");
                    for (Contact c : list) {
                        int id = c.getContactId();
                        int age = c.getContactAge();
                        String areaCode = c.getAreaCode();
                        dataPrepareMapper.setContactIdentity(id, IdCardUtil.generateIDByAgeAndAreaCode(age, areaCode));
                    }
                    System.out.println("sub contact task over...");
                }
            });
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) t.join();

        threadPool.shutdown();
    }
}
