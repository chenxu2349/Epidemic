package com.example.epidemic.test;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.epidemic.mapper.MpAreaMapper;
import com.example.epidemic.mapper.MpAreaService;
import com.example.epidemic.pojo.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MpSampleTest {

    @Autowired
    MpAreaMapper mpAreaMapper;

    @Autowired
    MpAreaService mpAreaService;

    @Test
    public void testSelect() {
        System.out.println(("----- select method test ------"));
        List<Area> areasList = mpAreaMapper.selectList(null);
        areasList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        System.out.println(("----- insert method test ------"));
        Area area0 = new Area(41, "10004", "合肥市瑶海区", "4141", 0.01, 0.01);
        List<Area> areaList = new ArrayList<>();
        Area area1 = new Area(42, "10004", "合肥市瑶海区", "4242", 0.01, 0.01);
        Area area2 = new Area(43, "10004", "合肥市瑶海区", "4343", 0.01, 0.01);
        Area area3 = new Area(44, "10004", "合肥市瑶海区", "4444", 0.01, 0.01);
        areaList.add(area1); areaList.add(area2); areaList.add(area3);
        System.out.println(mpAreaMapper.insert(area0));
    }

    @Test
    public void testQueryWrapper() {
        System.out.println(("----- queryWrapper test ------"));

        // 1.查询出area_id为奇数的所有小学，中学，大学地点信息
        QueryWrapper<Area> wrapper = new QueryWrapper<Area>()
                .select("*")
                .like("regional_location", "小学");

        List<Area> areaList = mpAreaMapper.selectList(wrapper);
        areaList.forEach(System.out::println);
    }
}
