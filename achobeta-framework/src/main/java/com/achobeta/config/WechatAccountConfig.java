package com.achobeta.config;
 
/**
 * @author Charles
 * @create 2023-05-15-13:05
 */
 
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
 
@Data
@Component
@ConfigurationProperties(prefix = "wechat") //读取配置配置中写的值
public class WechatAccountConfig {
    private String mpAppId;
 
    private String mpAppSecret;
}