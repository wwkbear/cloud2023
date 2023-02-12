package com.wwk.order;

import com.wwk.myrule.RibbonMyRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author wwkbear
 * @create 2023-02-01-15:49
 */
@SpringBootApplication
@EnableEurekaClient
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = RibbonMyRule.class)
    //name = "CLOUD-PAYMENT-SERVICE 必须与eureka上的服务命大小写必须保持一致。全小写不行。
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
