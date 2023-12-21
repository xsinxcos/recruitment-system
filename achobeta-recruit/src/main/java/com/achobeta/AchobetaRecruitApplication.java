package com.achobeta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("com.achobeta.mapper")
public class AchobetaRecruitApplication {

    public static void main(String[] args) {
        SpringApplication.run(AchobetaRecruitApplication.class ,args);
    }

}
