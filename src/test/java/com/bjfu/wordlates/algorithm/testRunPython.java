package com.bjfu.wordlates.algorithm;

import com.bjfu.wordlates.service.impl.PythonService;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testRunPython {
    /**
     * post请求json数据(添加请求头)并传递表单参数
     */
    @Test
    public void testApriori() throws JSONException {
        String url = "http://127.0.0.1:8000/api/apriori";
        String column[] = new String[]{"抽样地点_2*","食品亚类","所在省份*"};

        PythonService pythonService = new PythonService();
        String result = pythonService.apriori(column, "utils/resource/tmp.csv","apriori");
        System.out.println(result);
    }

}
