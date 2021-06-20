package com.bjfu.wordlates.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PythonConfig {

    public static String serviceUrl;

    @Value("${python.url}")
    public void setURL(String serviceUrl) {
        PythonConfig.serviceUrl = serviceUrl;
    }
}
