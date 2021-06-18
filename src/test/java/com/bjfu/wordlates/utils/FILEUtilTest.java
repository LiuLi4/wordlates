package com.bjfu.wordlates.utils;

import com.bjfu.wordlates.constant.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FILEUtilTest {

    @Test
    public void readCSVTest() throws IOException {
        Map<String, List<String>> dataMap = FILEUtil.readCSV(Constants.FILE_CSV_PATH);
        List<String> o=dataMap.get("抽样地点_2*");
        System.out.print(DATAUtil.culQuality(10,1));
    }
}
