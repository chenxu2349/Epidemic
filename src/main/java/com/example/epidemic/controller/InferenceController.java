package com.example.epidemic.controller;

import com.example.epidemic.mapper.UtilsMapper;
import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.Patient;
import com.example.epidemic.pojo.Statistics;
import com.example.epidemic.service.InferenceService;
import com.example.epidemic.utils.BasicInformation;
import com.example.epidemic.utils.RandomGenerator;
import com.example.epidemic.utils.ThreadPoolFactory;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
public class InferenceController {

    @Autowired
    private UtilsMapper utilsMapper;
    @Autowired
    private InferenceService inferenceService;
    private Logger logger = LoggerFactory.getLogger(InferenceController.class);
    // 全部区域
    private static List<String> areaCodePool = null;
    // 全部日期
    private static List<String> datePool = null;

    // 查看某一天某区域的患者
    @GetMapping("/getPatientsByDateAndAreaCode")
    @ResponseBody
    public List<Patient> getPatients(@PathParam("date") String date, @PathParam("areaCode") String areaCode) {

        long start = System.currentTimeMillis();

        List<Patient> patients = new LinkedList<>();
        for (Patient p : inferenceService.getPatientsByDate(date, areaCode)) patients.add(p);

        if (patients.size() == 0) logger.warn("getPatientsByDateAndAreaCode: No Query Data...");
        long end = System.currentTimeMillis();
        System.out.println("getPatientsByDateAndAreaCode执行用时：" + (end - start) + "ms");

        return patients;
    }

    // 查看某一天全部区域的患者
    @GetMapping("/getPatientsByDateAndCityCode")
    @ResponseBody
    public List<Patient> getPatients1(@PathParam("date") String date, @PathParam("cityCode") String cityCode) {

        long start = System.currentTimeMillis();

        List<Patient> patients = new LinkedList<>();
//        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
        for (String areaCode : areaCodePool) {
            if (!areaCode.startsWith(cityCode)) continue;
            for (Patient p : inferenceService.getPatientsByDate(date, areaCode)) patients.add(p);
        }

        if (patients.size() == 0) logger.warn("getPatientsByDate: No Query Data...");
        long end = System.currentTimeMillis();
        System.out.println("getPatientsByDate执行用时：" + (end - start) + "ms");

        return patients;
    }

    // 查看某个患者的接触者
    @GetMapping("/findContacts")
    @ResponseBody
    public List<Contact> getContacts(@PathParam("patient_id") int patient_id, @PathParam("date") String date) {

        long start = System.currentTimeMillis();

        Patient p = inferenceService.getPatientById(patient_id);
        List<Contact> contacts = new LinkedList<>();
        if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
        for (String areaCode : areaCodePool) {
            List<Contact> list = inferenceService.getContacts(p, areaCode, date);
            if (list != null) {
                for (Contact c : list) contacts.add(c);
            }
        }

        if (contacts.size() == 0) logger.warn("findContacts: No Query Data...");
        long end = System.currentTimeMillis();
        System.out.println("findContacts执行用时：" + (end - start) + "ms");
        return contacts;
    }

    // 推理运算
    @GetMapping("/infer")
    @ResponseBody
    public List<Contact> inference(@PathParam("patient_id") int patient_id, @PathParam("date") String date) throws ParseException, InterruptedException {

        long start = System.currentTimeMillis();

        Patient p = inferenceService.getPatientById(patient_id);
//        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<Contact> contacts = new LinkedList<>();
        if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
        for (String areaCode : areaCodePool) {
            List<Contact> list = inferenceService.getContacts(p, areaCode, date);
            if (list != null) {
                for (Contact c : list) contacts.add(c);
            }
        }
        if (contacts.size() == 0) logger.warn("infer: No Query Data...");

        // old version
        List<Contact> contacts1 = inferenceService.inference(p, contacts);

        long end = System.currentTimeMillis();
        System.out.println("infer执行用时：" + (end - start) + "ms");
        return contacts1;
    }

    // 推理全部患者的接触者概率，刷新数据库所有接触者患病概率
    // TODO 耗时过长
//    @GetMapping("/inferAll")
//    @ResponseBody
//    public void inferAll() throws ParseException, InterruptedException {
//
//        List<Patient> allPatients = inferenceService.getAllPatients();
//        List<List<Patient>> lists = ListUtils.partition(allPatients, 500);
//        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();
//
//        for (List<Patient> list : lists) {
//            threadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("new thread started...");
//
//                    for (Patient p : list) {
//                        List<Contact> contacts = new LinkedList<>();
//                        if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
//                        for (String areaCode : areaCodePool) {
//                            if (datePool == null) datePool = utilsMapper.getAllDates();
//                            for (String date : datePool) {
//                                // 获取这一天这个地区的该患者的所有接触者
//                                for (Contact c : inferenceService.getContacts(p, areaCode, date)) contacts.add(c);
//                                try {
//                                    inferenceService.inference(p, contacts);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//
//                    System.out.println("thread computation task complete...");
//                }
//            });
//        }
//
//        threadPool.shutdown();
//
//    }

    @GetMapping("/inferAll")
    @ResponseBody
    public void inferAll() throws ParseException, InterruptedException {

        System.out.println("computation started...");
        List<Patient> allPatients = inferenceService.getAllPatients();

        for (Patient p : allPatients) {
            List<Contact> contacts = new LinkedList<>();
            if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
            for (String areaCode : areaCodePool) {
                if (datePool == null) datePool = utilsMapper.getAllDates();
                for (String date : datePool) {
                    // 获取这一天这个地区的该患者的所有接触者
                    List<Contact> list = inferenceService.getContacts(p, areaCode, date);
                    if (list != null) {for (Contact c : list) contacts.add(c);}
                }
            }
            // 推理
            try {
                inferenceService.inference(p, contacts);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            System.out.println(p.getPatientName() + " computation complete...");
        }
        System.out.println("All task complete...");
    }


    // 所有接触者概率清零
    @GetMapping("/clearAllPossibility")
    @ResponseBody
    public void clearAll() {
        long start = System.currentTimeMillis();
        inferenceService.clearAllPossibility();
        long end = System.currentTimeMillis();
        System.out.println("clearAllPossibility执行用时：" + (end - start) + "ms");
    }

    // 统计某城市各区域患者和潜在患者数量
    @GetMapping("/countPatientAndPotential")
    @ResponseBody
    public List<Statistics> areaCount(@PathParam("date") String date, @PathParam("cityCode") String cityCode) {

        long start = System.currentTimeMillis();

//        String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
        List<Statistics> ans = new LinkedList<>();
        if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
        for (String areaCode : areaCodePool) {
            if (!areaCode.startsWith(cityCode)) continue;
            Statistics s = new Statistics();
            s.setAreaCode(areaCode);
            s.setPatient(inferenceService.countPatient(areaCode, date));
            s.setPotential_patient(inferenceService.countPotentialPatient(areaCode, date));
            ans.add(s);
        }

        if (ans.size() == 0) logger.warn("countPatientAndPotential: No Query Data...");
        long end = System.currentTimeMillis();
        System.out.println("countPatientAndPotential执行用时：" + (end - start) + "ms");
        return ans;
    }

    // 趋势预测，预测后两天的患者和潜在患者数量
    @GetMapping("/forecast")
    @ResponseBody
    public Map<Integer, int[]> trendForecast(@RequestParam("areaCode") String areaCode, @RequestParam("date") String date) {

        long start = System.currentTimeMillis();

        Map<Integer, int[]> map = new HashMap<>();
//        String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
        double[] randomPool1 = new double[]{1.1, 1.2, 1.3};
        double[] randomPool2 = new double[]{1.3, 1.4, 1.5};

        // 今天，明天，后天预测数据(患者，潜在患者)
        int p, pp;
        if (areaCode.equals("all")) {
            p = 0;
            pp = 0;
            if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
            for (String areaCode1 : areaCodePool) {
                p += inferenceService.countPatient(areaCode1, date);
                pp += inferenceService.countPotentialPatient(areaCode1, date);
            }
        } else {
            p = inferenceService.countPatient(areaCode, date);
            pp = inferenceService.countPotentialPatient(areaCode, date);
        }

        int seed1 = RandomGenerator.getRandomInt(0, 2);
        int seed2 = RandomGenerator.getRandomInt(0, 2);
        int seed3 = RandomGenerator.getRandomInt(0, 2);
        int seed4 = RandomGenerator.getRandomInt(0, 2);
        int[] arr0 = new int[]{p, pp};
        int[] arr1 = new int[]{(int) (p * randomPool1[seed1]), (int) (pp * randomPool1[seed2])};
        int[] arr2 = new int[]{(int) (p * randomPool2[seed3]), (int) (pp * randomPool2[seed4])};
        map.put(0, arr0);
        map.put(1, arr1);
        map.put(2, arr2);

        long end = System.currentTimeMillis();
        System.out.println("forecast执行用时：" + (end - start) + "ms");
        return map;
    }
}
