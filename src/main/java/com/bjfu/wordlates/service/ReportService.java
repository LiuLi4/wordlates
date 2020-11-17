package com.bjfu.wordlates.service;

import javax.servlet.http.HttpServletResponse;

public interface ReportService {
    String creatWordByTemplate(HttpServletResponse res) throws Exception;

}
