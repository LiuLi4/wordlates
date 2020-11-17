package com.bjfu.wordlates.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(String name,
                String md5,
                MultipartFile file) throws IOException;
}
