package com.bjfu.wordlates.controller;

import com.bjfu.wordlates.constant.ErrorEnums;
import com.bjfu.wordlates.service.FileService;
import com.bjfu.wordlates.utils.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/file")
@CrossOrigin
public class UploadFileController {

    private final FileService fileService;

    public UploadFileController(FileService fileService) {
        this.fileService = fileService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileController.class);

    @PostMapping(value = "/upload")
    public String upload(String name,
                       String md5,
                       MultipartFile file){
        String status = "";
        try {
            fileService.upload(name, md5, file);
        } catch (IOException e) {
            status = JSONUtil.statusToJson(ErrorEnums.E_500);
            e.printStackTrace();
            LOGGER.error("URL of the current request:[{}] - exception:[{}] - file:[{}]",
                    "/word/updateWord",
                    e.toString(),
                    name);
        }
        return status;
    }
}
