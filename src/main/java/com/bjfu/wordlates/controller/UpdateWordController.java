package com.bjfu.wordlates.controller;

import com.bjfu.wordlates.service.ReportService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@ResponseBody
@CrossOrigin
@RequestMapping(value = "/word", produces = "application/json;charset=UTF-8")
public class UpdateWordController {

    private final ReportService reportService;

    public UpdateWordController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(value = "/uploadWord")
    @ResponseBody
    public String updateWord(String name,
                             String md5,
                             MultipartFile file) {
        return reportService.creatWordByTemplate();
    }
}
