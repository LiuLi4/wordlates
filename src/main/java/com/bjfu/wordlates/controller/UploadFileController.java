package com.bjfu.wordlates.controller;

import com.bjfu.wordlates.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/file")
@CrossOrigin
public class UploadFileController {

    private final FileService fileService;

    public UploadFileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public void upload(String name,
                       String md5,
                       MultipartFile file){
        fileService.upload(name, md5, file);
    }
}
