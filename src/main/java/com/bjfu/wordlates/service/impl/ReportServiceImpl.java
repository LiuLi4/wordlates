package com.bjfu.wordlates.service.impl;

import com.bjfu.wordlates.constant.Constants;
import com.bjfu.wordlates.constant.ErrorEnums;
import com.bjfu.wordlates.service.ReportService;
import com.bjfu.wordlates.utils.FILEUtil;
import com.bjfu.wordlates.utils.JSONUtil;
import com.bjfu.wordlates.utils.POIUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("testUserService")
public class ReportServiceImpl implements ReportService {


    @Override
    public String creatWordByTemplate(HttpServletResponse res) throws Exception {
        String fileName = Constants.FILE_OUT_NAME;
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream os = res.getOutputStream();

        // 数据
        Map<String, List<String>> dataMap = FILEUtil.readCSV(Constants.FILE_CSV_PATH);
        // 计算对应标签

        // 生成自定义标签的值
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("foodName", "茶叶");

        // 模板word文件相对路径
        String wordSrcPath = Constants.FILE_TEMPLATE_PATH;
        POIUtil.templateWrite(wordSrcPath, os, params);

        return JSONUtil.statusToJson(ErrorEnums.E_200);
    }
}
