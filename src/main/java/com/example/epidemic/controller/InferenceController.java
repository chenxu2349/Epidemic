package com.example.epidemic.controller;

import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.Patient;
import com.example.epidemic.pojo.Statistics;
import com.example.epidemic.service.InferenceService;
import com.example.epidemic.utils.GetAllDates;
import com.example.epidemic.utils.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class InferenceController {

    private Logger logger = LoggerFactory.getLogger(InferenceController.class);
    private static String[] areaPool = new String[]{"10001","10002","10003","10004"};

    @Autowired
    private InferenceService inferenceService;

    // 查看某一天某区域的患者
    @GetMapping("/getPatients")
    @ResponseBody
    public List<Patient> getPatients(@PathParam("date") String date, @PathParam("areaCode") String areaCode) {
        List<Patient> patients = new LinkedList<>();
        for (Patient p : inferenceService.getPatientsByDate(date, areaCode)) patients.add(p);
        if (patients.size() == 0) {
            logger.warn("No Query Data...");
            return null;
        } else return patients;
    }

    // 查看某一天全部区域的患者
    @GetMapping("/getPatientsByDate")
    @ResponseBody
    public List<Patient> getPatients(@PathParam("date") String date) {
        List<Patient> patients = new LinkedList<>();
//        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        for (String areaCode : areaPool) {
            for (Patient p : inferenceService.getPatientsByDate(date, areaCode)) patients.add(p);
        }
        if (patients.size() == 0) {
            logger.warn("No Query Data...");
            return null;
        } else return patients;
    }

    // 查看某个患者的接触者
    @GetMapping("/findContacts")
    @ResponseBody
    public List<Contact> getContacts(@PathParam("patient_id") int patient_id, @PathParam("date") String date) {
//        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<Contact> contacts = new LinkedList<>();
        for (String areaCode : areaPool) {
            for (Contact c : inferenceService.getContacts(patient_id, areaCode, date)) contacts.add(c);
        }
        if (contacts.size() == 0) return null;
        else return contacts;
    }

    // 推理运算
    @GetMapping("/infer")
    @ResponseBody
    public List<Contact> inference(@PathParam("patient_id") int patient_id, @PathParam("date") String date) throws ParseException {
        Patient p = inferenceService.getPatientById(patient_id);
//        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<Contact> contacts = new LinkedList<>();
        for (String areaCode : areaPool) {
            for (Contact c : inferenceService.getContacts(patient_id, areaCode, date)) contacts.add(c);
        }
        if (contacts.size() == 0) return null;
        else return inferenceService.inference(p, contacts);
    }

    // 推理全部患者的接触者概率，刷新数据库所有接触者患病概率
    @GetMapping("/inferAll")
    @ResponseBody
    public void inferAll() throws ParseException {
        List<Patient> allPatients = inferenceService.getAllPatients();
//        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        for (Patient p : allPatients) {
            List<Contact> contacts = new LinkedList<>();
            for (String areaCode : areaPool) {
                List<String> allDates = GetAllDates.getAllDates();
                for (String date : allDates) {
                    // 获取这一天这个地区的该患者的所有接触者
                    for (Contact c : inferenceService.getContacts(p.getPatientId(), areaCode, date)) contacts.add(c);
                    // 推理
                    inferenceService.inference(p, contacts);
                }
            }
        }
    }

    // 所有接触者概率清零
    @GetMapping("/clearAllPossibility")
    @ResponseBody
    public void clearAll() {
        inferenceService.clearAllPossibility();
    }

    // 统计各区域患者和潜在患者数量
    @GetMapping("/countPatientAndPotential")
    @ResponseBody
    public List<Statistics> areaCount(@PathParam("date") String date) {
//        String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
        List<Statistics> ans = new LinkedList<>();
        for (String areaCode : areaPool) {
            Statistics s = new Statistics();
            s.setAreaCode(areaCode);
            s.setPatient(inferenceService.countPatient(areaCode, date));
            s.setPotential_patient(inferenceService.countPotentialPatient(areaCode, date));
            ans.add(s);
        }
        return ans;
    }

    // 趋势预测，预测后两天的患者和潜在患者数量
    @GetMapping("/forecast")
    @ResponseBody
    public Map<Integer, int[]> trendForecast(@RequestParam("areaCode") String areaCode, @RequestParam("date") String date) {

        Map<Integer, int[]> map = new HashMap<>();
//        String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
        double[] randomPool1 = new double[]{1.1, 1.2, 1.3};
        double[] randomPool2 = new double[]{1.3, 1.4, 1.5};

        // 今天，明天，后天预测数据(患者，潜在患者)
        int p, pp;
        if (areaCode.equals("all")) {
            p = 0;
            pp = 0;
            for (String areaCode1 : areaPool) {
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

        return map;
    }
}
