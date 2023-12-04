package com.example.epidemic;

import com.example.epidemic.mapper.DataPrepareMapper;
import com.example.epidemic.service.DataPrepareService;
import com.example.epidemic.utils.ThreadPoolFactory;
import com.example.epidemic.utils.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
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
}
