package com.bjfu.wordlates.utils;

import com.alibaba.fastjson.JSONObject;
import com.bjfu.wordlates.constant.ErrorEnums;

public class JSONUtil {
    public static String statusToJson(ErrorEnums errorEnum){
        JSONObject result = new JSONObject();
        result.put("code", errorEnum.getCode());
        result.put("msg", errorEnum.getMsg());

        return result.toJSONString();
    }
}
