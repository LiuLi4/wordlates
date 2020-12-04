package com.bjfu.wordlates.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataDaoTest {

    @Autowired
    private DataDao dao;

    @Resource
    private ReportDao reportDao;

    @Test
    public void testSelect(){
        System.out.println(dao.creatTable("2020_12_04_data"));
    }

    @Test
    public void test(){
        System.out.println(reportDao.selectById(1L).getFoodName());
    }
}
