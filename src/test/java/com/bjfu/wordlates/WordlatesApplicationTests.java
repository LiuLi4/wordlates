package com.bjfu.wordlates;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.bjfu.wordlates.entity")
class WordlatesApplicationTests {

    @Test
    void contextLoads() {
    }

}
