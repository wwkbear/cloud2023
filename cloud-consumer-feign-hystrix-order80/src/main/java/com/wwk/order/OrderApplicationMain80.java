package com.wwk.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wwkbear
 * @create 2023-02-10-23:55
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class OrderApplicationMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplicationMain80.class,args);
    }
}
