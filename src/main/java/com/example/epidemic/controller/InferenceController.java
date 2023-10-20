package com.example.epidemic.controller;

import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.Patient;
import com.example.epidemic.pojo.Statistics;
import com.example.epidemic.service.InferenceService;
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

    @Autowired
    private InferenceService inferenceService;

    // 查看某一天的患者
    @GetMapping("/getPatients")
    @ResponseBody
    public List<Patient> getPatients(@PathParam("batch") int batch, @PathParam("areaCode") String areaCode) {
        List<Patient> patients = new LinkedList<>();
        for (Patient p : inferenceService.getPatients(batch, areaCode)) patients.add(p);
        if (patients.size() == 0) return null;
        else return patients;
    }

    // 查看某一天全部区域的患者
    @GetMapping("/getPatientsByDate")
    @ResponseBody
    public List<Patient> getPatients(@PathParam("batch") int batch) {
        List<Patient> patients = new LinkedList<>();
        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        for (String areaCode : areaPool) {
            for (Patient p : inferenceService.getPatients(batch, areaCode)) patients.add(p);
        }
        if (patients.size() == 0) return null;
        else return patients;
    }

    // 查看某个患者的接触者
    @GetMapping("/findContacts")
    @ResponseBody
    public List<Contact> getContacts(@PathParam("patient_id") int patient_id, @PathParam("batch")int batch) {
        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<Contact> contacts = new LinkedList<>();
        for (String areaCode : areaPool) {
            for (Contact c : inferenceService.getContacts(patient_id, areaCode, batch)) contacts.add(c);
        }
        if (contacts.size() == 0) return null;
        else return contacts;
    }

    // 推理运算
    @GetMapping("/infer")
    @ResponseBody
    public List<Contact> inference(@PathParam("patient_id") int patient_id, @PathParam("batch")int batch) throws ParseException {
        Patient p = inferenceService.getPatient(patient_id);
        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<Contact> contacts = new LinkedList<>();
        for (String areaCode : areaPool) {
            for (Contact c : inferenceService.getContacts(patient_id, areaCode, batch)) contacts.add(c);
        }
        if (contacts.size() == 0) return null;
        else return inferenceService.inference(p, contacts);
    }

    // 推理全部患者的接触者概率，刷新数据库所有接触者患病概率
    @GetMapping("/inferAll")
    @ResponseBody
    public void inferAll() throws ParseException {
        List<Patient> allPatients = inferenceService.getAllPatients();
        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        for (Patient p : allPatients) {
            List<Contact> contacts = new LinkedList<>();
            for (String areaCode : areaPool) {
                for (int batch = 1; batch <= 3; batch++) {
                    // 获取这一天这个地区的该患者的所有接触者
                    for (Contact c : inferenceService.getContacts(p.getPatientId(), areaCode, batch)) contacts.add(c);

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
    public List<Statistics> areaCount(@PathParam("batch") int batch) {
        String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
        List<Statistics> ans = new LinkedList<>();
        for (String areaCode : areaPool) {
            Statistics s = new Statistics();
            s.setAreaCode(areaCode);
            s.setPatient(inferenceService.countPatient(areaCode, batch));
            s.setPotential_patient(inferenceService.countPotentialPatient(areaCode, batch));
            ans.add(s);
        }
        return ans;
    }

    // 趋势预测，预测后两天的患者和潜在患者数量
    @GetMapping("/forecast")
    @ResponseBody
    public Map<Integer, int[]> trendForecast(@RequestParam("areaCode") String areaCode, @RequestParam("batch") int batch) {
        Map<Integer, int[]> map = new HashMap<>();
        // 今天，明天，后天预测数据(患者，潜在患者)
        if (areaCode.equals("all")) {
            String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
            int p = 0, pp = 0;
            for (String areaCode1 : areaPool) {
                p += inferenceService.countPatient(areaCode1, batch);
                pp += inferenceService.countPotentialPatient(areaCode1, batch);
            }
            int seed1 = (int)(-3 + Math.random()*(3 - (-3) + 1));
            int seed2 = (int)(-3 + Math.random()*(3 - (-3) + 1));
            int seed3 = (int)(-3 + Math.random()*(3 - (-3) + 1));
            int seed4 = (int)(-3 + Math.random()*(3 - (-3) + 1));
            int[] arr0 = new int[]{p, pp};
            int[] arr1 = new int[]{p + seed1, pp + seed2};
            int[] arr2 = new int[]{p + seed3, pp + seed4};
            map.put(0, arr0);
            map.put(1, arr1);
            map.put(2, arr2);
        } else {
            int p = inferenceService.countPatient(areaCode, batch);
            int pp = inferenceService.countPotentialPatient(areaCode, batch);
            int seed1 = (int)(-3 + Math.random()*(3 - (-3) + 1));
            int seed2 = (int)(-3 + Math.random()*(3 - (-3) + 1));
            int seed3 = (int)(-3 + Math.random()*(3 - (-3) + 1));
            int seed4 = (int)(-3 + Math.random()*(3 - (-3) + 1));
            int[] arr0 = new int[]{p, pp};
            int[] arr1 = new int[]{p + seed1, pp + seed2};
            int[] arr2 = new int[]{p + seed3, pp + seed4};
            map.put(0, arr0);
            map.put(1, arr1);
            map.put(2, arr2);
        }
        return map;
    }
}
