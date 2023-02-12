package com.wwk.feignorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wwkbear
 * @create 2023-02-08-17:40
 */
@SpringBootApplication
@EnableFeignClients
public class FeignOrderApplication80 {
    public static void main(String[] args) {
        SpringApplication.run(FeignOrderApplication80.class,args);
    }
}
