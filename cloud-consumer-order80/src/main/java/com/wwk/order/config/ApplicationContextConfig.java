package com.wwk.order.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wwkbear
 * @create 2023-02-01-15:54
 */
@Configuration
public class ApplicationContextConfig {
    //注入RestTemplate
    @Bean
    @LoadBalanced //使用@LoadBalanced注解赋予RestTemplate负载均衡的能力(即开启Ribbon)
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
