package com.bjfu.wordlates.controller;

import com.bjfu.wordlates.service.ReportService;
import com.bjfu.wordlates.utils.POIUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ResponseBody
@CrossOrigin
@RequestMapping(value = "/word", produces = "application/json;charset=UTF-8")
public class UpdateWordController {

    private final ReportService reportService;

    public UpdateWordController(ReportService reportService) {
        this.reportService = reportService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateWordController.class);

    @GetMapping(value = "/updateWord")
    @ResponseBody
    public String updateWord(HttpServletResponse res) {
        String status = "";
        try {
            status = reportService.creatWordByTemplate(res);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("/word/updateWord: " + e.toString());
        }
        return status;
    }
}
