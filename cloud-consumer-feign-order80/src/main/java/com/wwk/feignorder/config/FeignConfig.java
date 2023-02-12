package com.wwk.feignorder.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fegin的配置类，替换掉默认的日志内容等级
 * @author wwkbear
 * @create 2023-02-09-16:20
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
