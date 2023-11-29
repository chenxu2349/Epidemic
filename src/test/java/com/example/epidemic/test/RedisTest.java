package com.example.epidemic.test;

import com.example.epidemic.mapper.AreaMapper;
import com.example.epidemic.pojo.Area;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public void splitCollectionTest() {

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
        }

    }
}
