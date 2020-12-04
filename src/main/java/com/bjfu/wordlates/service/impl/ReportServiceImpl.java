package com.bjfu.wordlates.service.impl;

import com.bjfu.wordlates.constant.Constants;
import com.bjfu.wordlates.constant.ErrorEnums;
import com.bjfu.wordlates.dao.ReportDao;
import com.bjfu.wordlates.service.ReportService;
import com.bjfu.wordlates.utils.JSONUtil;
import com.bjfu.wordlates.utils.POIUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("testUserService")
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportDao dao;

    @Override
    public String creatWordByTemplate(HttpServletResponse res) throws Exception {
        String fileName = Constants.FILE_OUT_NAME;
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream os = res.getOutputStream();

        // 生成自定义标签的值
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("foodName", dao.selectById(1L).getFoodName());
        params.put("inspectionInstituteName", dao.selectById(1L).getInspectionInstituteName());
        params.put("companyName", dao.selectById(1L).getCompanyName());
        params.put("testingInstituteName", dao.selectById(1L).getTestingInstituteName());
        params.put("newDate", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));//当前时间

        // 模板word文件相对路径
        String wordSrcPath = Constants.FILE_TEMPLATE_PATH;
        POIUtil.templateWrite(wordSrcPath, os, params);

        return JSONUtil.statusToJson(ErrorEnums.E_200);
    }
}
