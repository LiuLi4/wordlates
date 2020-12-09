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

    @Test
    public void testSelect(){
        System.out.println(dao.creatTable("2020_12_04_data"));
    }
}
