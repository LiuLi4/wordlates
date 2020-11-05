package com.bjfu.wordlates.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.bjfu.wordlates.entity")
public class DataSourceConfig {

}
