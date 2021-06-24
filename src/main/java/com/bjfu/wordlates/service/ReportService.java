package com.bjfu.wordlates.service;

import com.bjfu.wordlates.entity.Message;

import javax.servlet.http.HttpServletResponse;

public interface ReportService {
    String creatWordByTemplate(HttpServletResponse res) throws Exception;
    void getMessage(Message message);
}
