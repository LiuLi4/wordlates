package com.bjfu.wordlates.controller;

import com.bjfu.wordlates.constant.ErrorEnums;
import com.bjfu.wordlates.service.ReportService;
import com.bjfu.wordlates.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
        } catch (Exception e) {
            status = JSONUtil.statusToJson(ErrorEnums.E_500);
            e.printStackTrace();
            LOGGER.error("URL of the current request:[{}] - exception:[{}] - response:[{}]",
                    "/word/updateWord",
                    e.toString(),
                    res);
        }
        return status;
    }
}
