package com.bjfu.wordlates.service.impl;

import com.bjfu.wordlates.constant.Constants;
import com.bjfu.wordlates.constant.ErrorEnums;
import com.bjfu.wordlates.dao.ReportDao;
import com.bjfu.wordlates.service.ReportService;
import com.bjfu.wordlates.utils.JSONUtil;
import com.bjfu.wordlates.utils.POIUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("testUserService")
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportDao dao;

    @Override
    public String creatWordByTemplate() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("foodName", dao.selectById(1L).getFoodName());
        params.put("inspectionInstituteName", dao.selectById(1L).getInspectionInstituteName());
        params.put("companyName", dao.selectById(1L).getCompanyName());
        params.put("testingInstituteName", dao.selectById(1L).getTestingInstituteName());
        params.put("newDate", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));//当前时间

        // 模板word文件真实路径
        String wordSrcPath = Constants.FILE_TEMPLATE_PATH ;
        // 使用该办件编号作为文件名称
        String wordDestPath = Constants.FILE_OUT_PATH;
        try {
            POIUtil.templateWrite(wordSrcPath, wordDestPath, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSONUtil.statusToJson(ErrorEnums.E_200);
    }
}
