package com.bjfu.wordlates.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.bjfu.wordlates.config.PythonConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PythonService {

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * post请求python service的apriori算法
     */
    public String apriori(String column[], String dataPath, String function) throws JSONException {
        //拼接出要请求的URL
        String url = PythonConfig.serviceUrl + function;

        //创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("columnList", column);
        jsonObject.put("dataPath", dataPath);

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        String result = responseEntity.getBody();
        JSONArray jsonArray = JSONArray.parseArray(result);
        return jsonArray.toJSONString();
    }
}
