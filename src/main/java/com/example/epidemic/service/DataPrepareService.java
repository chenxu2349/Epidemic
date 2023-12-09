package com.example.epidemic.service;

import com.example.epidemic.mapper.DataPrepareMapper;
import com.example.epidemic.pojo.ContactTrack;
import com.example.epidemic.pojo.PatientTrack;
import com.example.epidemic.utils.BasicInformation;
import com.example.epidemic.utils.ThreadPoolFactory;
import com.example.epidemic.utils.TimeUtil;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DataPrepareService
 * @Description
 * @Author chenxu
 * @Date 2023/12/2 22:56
 **/
@Service
public class DataPrepareService {
    @Autowired
    private DataPrepareMapper mp1;

    // 多线程实现
    // 只匹配一个区内的信息
    public void check(String areaCode) throws ParseException, InterruptedException {

//        List<PatientTrack> allPti = mp1.getAllPti();
//        List<ContactTrack> allCti = mp1.getAllCti();
        int start = BasicInformation.getAreaCodeStart(areaCode);
        int end = BasicInformation.getAreaCodeEnd(areaCode);
        List<PatientTrack> allPti = mp1.getPtiByRange(start, end);
        List<ContactTrack> allCti = mp1.getCtiByRange(start, end);

//        String code = mp1.getAreaCodeById(3);

        // 切分allPti
        List<List<PatientTrack>> lists = ListUtils.partition(allPti, 50);
        List<Thread> threads = new ArrayList<>();

        // 创建线程池
        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();

        for (List<PatientTrack> subPtiList : lists) {
            // 启用一个线程去匹配subPtiList里的患者轨迹和全部接触者轨迹
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread" + Math.random() + "has started...");
                    for (PatientTrack pti : subPtiList) {
                        for (ContactTrack cti : allCti) {
                            int cId = cti.getContactId();
                            int pId = pti.getPatientId();
                            String s1 = pti.getStartTime();
                            String e1 = pti.getEndTime();
                            String s2 = cti.getStartTime();
                            String e2 = cti.getEndTime();
//                            System.out.println("running....");
                            // 如果患者和接触者有接触
                            try {
                                if (pti.getAreaId() == cti.getAreaId() && hasIntersection(s1, e1, s2, e2)) {
                                    // 填写接触者表中的患者ID
                                    //                    mp1.setPatient(cti.getId(), cId, pId);
                                    int contactTime = contactTime(s1, e1, s2, e2);
                                    int contactArea = cti.getAreaId();
                                    // 填写患者和接触者之间关系属性
                                    mp1.setRelation0(cId, pId, contactTime, contactArea);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    System.out.println("subTask complete...");
                }
            });
            Thread.sleep(500);
        }
        // 关闭线程池
        threadPool.shutdown();
    }

    // 普通实现
//    public void check(String areaCode) throws ParseException, InterruptedException {
//
//        int start = BasicInformation.getAreaCodeStart(areaCode);
//        int end = BasicInformation.getAreaCodeEnd(areaCode);
//        List<PatientTrack> allPti = mp1.getPtiByRange(start, end);
//        List<ContactTrack> allCti = mp1.getCtiByRange(start, end);
//
//        System.out.println(areaCode + "subTask starting...");
//        for (PatientTrack pti : allPti) {
//            for (ContactTrack cti : allCti) {
//                int cId = cti.getContactId();
//                int pId = pti.getPatientId();
//                String s1 = pti.getStartTime();
//                String e1 = pti.getEndTime();
//                String s2 = cti.getStartTime();
//                String e2 = cti.getEndTime();
////                            System.out.println("running....");
//                // 如果患者和接触者有接触
//                try {
//                    if (pti.getAreaId() == cti.getAreaId() && hasIntersection(s1, e1, s2, e2)) {
//                        // 填写接触者表中的患者ID
//                        //                    mp1.setPatient(cti.getId(), cId, pId);
//                        int contactTime = contactTime(s1, e1, s2, e2);
//                        int contactArea = cti.getAreaId();
//                        // 填写患者和接触者之间关系属性
//                        mp1.setRelation0(cId, pId, contactTime, contactArea);
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("subTask complete...");
//    }

    public boolean hasIntersection(String s1, String e1, String s2, String e2) throws ParseException {
        // String类型转为日期类型比较
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 时间戳，毫秒
        long ps = format.parse(s1).getTime();
        long pe = format.parse(e1).getTime();
        long cs = format.parse(s2).getTime();
        long ce = format.parse(e2).getTime();

        if (cs >= ps && cs <= pe) return true;
        if (ce >= ps && ce <= pe) return true;
        if (ps >= cs && ps <= ce) return true;
        if (pe >= cs && pe <= ce) return true;

        return false;
    }

    public int contactTime(String p_start, String p_end, String c_start, String c_end) throws ParseException {

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

    public void clearRelation0() {
        mp1.clearRelation0();
    }

    public void enlargeTrackTimeSpan() throws InterruptedException {

        List<PatientTrack> allPti = mp1.getAllPti();
        List<ContactTrack> allCti = mp1.getAllCti();

        List<List<PatientTrack>> ptiLists = ListUtils.partition(allPti, 3000);
        List<List<ContactTrack>> ctiLists = ListUtils.partition(allCti, 3000);

        List<Thread> threadList = new ArrayList<>();
        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();

        for (List<PatientTrack> list : ptiLists) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("pti task start...");
                    for (PatientTrack pt : list) {
                        String start = pt.getStartTime();
                        String end = pt.getEndTime();
                        String newStart = TimeUtil.enlargeStart(start);
                        String newEnd = TimeUtil.enlargeEnd(end);
                        mp1.setPtiTimeById(pt.getPatientTrackId(), newStart, newEnd);
                    }
                    System.out.println("pti task end...");
                }
            });
            t.start();
            threadList.add(t);
        }

        for (List<ContactTrack> list : ctiLists) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("cti task start...");
                    for (ContactTrack ct : list) {
                        String start = ct.getStartTime();
                        String end = ct.getEndTime();
                        String newStart = TimeUtil.enlargeStart(start);
                        String newEnd = TimeUtil.enlargeEnd(end);
                        mp1.setCtiTimeById(ct.getContactTrackId(), newStart, newEnd);
                    }
                    System.out.println("cti task end...");
                }
            });
            t.start();
            threadList.add(t);
        }

        for (Thread t : threadList) t.join();

        threadPool.shutdown();
    }
}
