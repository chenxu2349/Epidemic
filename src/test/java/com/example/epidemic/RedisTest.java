package com.example.epidemic;

import com.example.epidemic.mapper.AreaMapper;
import com.example.epidemic.pojo.Area;
import com.example.epidemic.utils.ThreadPoolFactory;
import org.apache.commons.collections4.ListUtils;
import org.apache.tomcat.jni.Time;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @ClassName RedisTest
 * @Description
 * @Author chenxu
 * @Date 2023/11/29 12:35
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private AreaMapper areaMapper;

    // 测试redis的连接
    @Test
    public void jedisTest() {
        String host = "101.35.244.157";
        int port = 6379;
        String password = "CX185813";
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        System.out.println(jedis.ping());
        Set<String> keys = jedis.keys("*");
        keys.forEach(System.out::println);
    }

    // 测试多线程操作数据库
    @Test
    public void multiSearch() {

        List<Area> allAreas = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            System.out.println("t1...");
            List<Area> areas = areaMapper.getAreas(0, 100);
            System.out.println("area1:"+areas.size());
            for (Area a : areas) {
                allAreas.add(a);
                System.out.println(a.getAreaName());
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2...");
            List<Area> areas = areaMapper.getAreas(101, 200);
            for (Area a : areas) {
                allAreas.add(a);
                System.out.println(a.getAreaName());
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        for (Area a : areaMapper.getAllAreas()) allAreas.add(a);

        System.out.println("All areas size: " + allAreas.size());
    }

    @Test
    public void splitCollectionTest() throws InterruptedException {

        List<Area> allAreas = areaMapper.getAllAreas();
        int batchSize = 40;

        // 使用Apache Commons Collections分工对集合进行切分
        List<List<Area>> lists = ListUtils.partition(allAreas, batchSize);
        for (List<Area> list : lists) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Area a : list) System.out.println(a.getAreaId() + ": " + a.getAreaName());
                }
            });
            t.start();
            t.join();
        }
    }

    // 计数
    int allAreaCount = 0, threadCount = 0;
    @Test
    public void threadPoolTest() throws InterruptedException {

        List<Area> allAreas = areaMapper.getAllAreas();
        // 分片
        List<List<Area>> subAreaLists = ListUtils.partition(allAreas, 20);
        // 创建线程池
//        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
//                5, 10, 20, TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy()
//        );
        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();
        // 任务分给线程池执行
        for (List<Area> subAreas : subAreaLists) {
            threadPool.execute(new Runnable() {
                // 子线程异步执行
                @Override
                public void run() {
                    System.out.println("thread has started...");
                    threadCount++;
                    for (Area a : subAreas) {
                        System.out.println("AreaId" + a.getAreaId() + ": " + a.getAreaName());
                        allAreaCount++;
                    }
                    System.out.println("task complete...");
                }
            });
        }
        //  关闭线程池
        // shutdown只中断空闲线程，然后等任务执行完再关闭
        // shutdownNow尝试停止所有正在执行任务或者暂停任务的线程，并返回等待执行任务的列表
        threadPool.shutdown();
        // 打印启动过的线程数和统计的总地点数
        Thread.sleep(5000);
        System.out.println("系统可用处理器核心数: " + Runtime.getRuntime().availableProcessors());
        System.out.println("启动过的线程总数: " + threadCount);
        System.out.println("统计的地点总数: " + allAreaCount);
    }
}
