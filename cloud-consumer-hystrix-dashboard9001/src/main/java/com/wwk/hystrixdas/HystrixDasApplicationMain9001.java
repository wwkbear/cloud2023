package com.wwk.hystrixdas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author wwkbear
 * @create 2023-02-11-16:39
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDasApplicationMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDasApplicationMain9001.class,args);
    }
}
