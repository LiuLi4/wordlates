package com.bjfu.wordlates.utils;

import com.bjfu.wordlates.constant.Constants;
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
        FILEUtil.readCSV(Constants.FILE_CSV_PATH);
    }
}
