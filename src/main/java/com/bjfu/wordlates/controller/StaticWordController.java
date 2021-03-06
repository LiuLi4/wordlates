package com.bjfu.wordlates.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjfu.wordlates.constant.Constants;
import com.bjfu.wordlates.constant.ErrorEnums;
import com.bjfu.wordlates.entity.Message;
import com.bjfu.wordlates.service.ReportService;
import com.bjfu.wordlates.service.impl.PythonService;
import com.bjfu.wordlates.utils.FILEUtil;
import com.bjfu.wordlates.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
public class StaticWordController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PythonService pythonService;
    /*模板请求*/
    @GetMapping("/static/food")
    public void staticWord(HttpServletRequest req,HttpServletResponse res) throws IOException {
        String fileName = "./upload/2018年XX食品年度分析报告（模板）.docx";
        String downloadName= "食品模板.docx";
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(downloadName, "UTF-8"));
        BufferedInputStream fis=new BufferedInputStream(new FileInputStream(fileName));
        BufferedOutputStream fos=new BufferedOutputStream(res.getOutputStream());
        byte[] b = new byte[1024];
        int len;
        while((len = fis.read(b))!=-1){
            fos.write(b, 0, len);
        }
        fos.flush();
        fos.close();
        fis.close();
    }

    /*基本信息上传*/
    @PostMapping("basicMessage")
    public String postBasicMessage(@RequestBody Message message){
        reportService.getMessage(message);
        if(message==null){
            return JSONUtil.statusToJson(ErrorEnums.E_500);
        }
        return JSONUtil.statusToJson(ErrorEnums.E_200);
    }

    /*分析数据列表返回*/
    @GetMapping("parseFile")
    public Set<String> getFileData() throws CharacterCodingException {
        Map<String, List<String>> dataMap = new HashMap<>();
        try {
            dataMap = FILEUtil.readCSV(Constants.FILE_CSV_PATH);
            System.out.println(dataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dataMap.keySet());
        return dataMap.keySet();
    }

    /*文档生成数据上传*/
    @PostMapping("uploadFile")
    public void postFileExcel(MultipartFile file) {
        String fileName=file.getOriginalFilename();
        assert fileName != null;
        int index=fileName.lastIndexOf(".");
        String str1=fileName.substring(index+1);
        String saveName=null;
        if(str1.equals("xlsx")){
            Constants.FILE_CSV_PATH = "./upload/1.xlsx";
            saveName= Constants.FILE_CSV_PATH;}
        else if(str1.equals("csv")){
            Constants.FILE_CSV_PATH = "./upload/1.csv";
            saveName= Constants.FILE_CSV_PATH;}
        else if(str1.equals("docx")){
            Constants.FILE_TEMPLATE_PATH = "./upload/1.docx";
            saveName= Constants.FILE_TEMPLATE_PATH;}
        else if(str1.equals("doc")){
            Constants.FILE_TEMPLATE_PATH = "./upload/1.doc";
            saveName= Constants.FILE_TEMPLATE_PATH;}
        FileOutputStream fos = null;
        try {
            assert saveName != null;
            fos = new FileOutputStream(saveName);
            fos.write(file.getBytes()); // 写入文件
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @PostMapping("getParseData")
    public String getParseData(@RequestBody String column){
        System.out.println(column);
        JSONObject jsonObject = JSON.parseObject(column);
        JSONArray columnTemp = jsonObject.getJSONArray("checkedCities");
        int length=columnTemp.size();
        String[] columns=new String[length];
        while(length>0){
            columns[length-1]= (String) columnTemp.get(length-1);
            --length;
        }
        System.out.println(columns.length+columns[1]+columns[0]+columns[2]);
        String r=pythonService.apriori(columns,"utils/resource/tmp.csv","apriori");
        return r;
    }

}
