package com.bjfu.wordlates.service.impl;

import com.bjfu.wordlates.config.UploadConfig;
import com.bjfu.wordlates.constant.ErrorEnums;
import com.bjfu.wordlates.dao.FileDao;
import com.bjfu.wordlates.entity.File;
import com.bjfu.wordlates.service.FileService;
import com.bjfu.wordlates.utils.FILEUtil;
import com.bjfu.wordlates.utils.JSONUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Resource
    private FileDao dao;

    @Override
    public String upload(String name, String md5, MultipartFile file) throws IOException {
        // 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
        // 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
        name = name.substring(name.lastIndexOf("\\") + 1);
        dao.save(new File(name, md5, UploadConfig.path, new Date()));
        String suffix = name.substring(name.length() - 5, name.length());
        if (suffix.equals(".csv")) {
            name = "data.csv";
        } else if (suffix.equals(".docx")) {
            name = "template.docx";
        }
        String path = UploadConfig.path + name;
        FILEUtil.write(path, file.getInputStream());
        return JSONUtil.statusToJson(ErrorEnums.E_200);
    }

}
