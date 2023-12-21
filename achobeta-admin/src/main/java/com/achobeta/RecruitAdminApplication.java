package com.achobeta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("com.achobeta.mapper")
public class RecruitAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitAdminApplication.class ,args);
    }
}