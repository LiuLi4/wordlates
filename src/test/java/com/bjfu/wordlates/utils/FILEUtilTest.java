package com.bjfu.wordlates.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FILEUtilTest {

    @Test
    public void readCSVTest() throws IOException {
        FILEUtil.readCSV("./upload/国抽系统数据导出格式.csv");
    }
}
