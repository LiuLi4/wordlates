package com.bjfu.wordlates.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void upload(String name,
                String md5,
                MultipartFile file);
}
