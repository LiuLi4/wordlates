package com.bjfu.wordlates.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ReportService {
    String creatWordByTemplate(HttpServletResponse res) throws IOException;

}
