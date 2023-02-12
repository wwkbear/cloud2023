package com.wwk.order.controller;


import com.wwk.order.lber.LbRule;
import com.wwk.springcloud.entities.CommonResult;
import com.wwk.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author wwkbear
 * @create 2023-02-01-15:51
 */
@RestController
@RequestMapping("/consumer/payment")
@Slf4j
public class OrderController {
    //临时编写一个URL测试，后续改成xml或者其他方式读取而不是写死。
    //private static final String PaymentSrv_URL = "http://localhost:8001";
    //使用eureka集群以及后端服务集群之后，就不能直接写死地址而是按照服务别名在eureka自动分配服务器，就无需写死地址。
    private static final String PaymentSrv_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    private LbRule lbRule;
    @Autowired
    private DiscoveryClient discoveryClient;

    //编写创建支付记录
    //客户端用浏览器是get请求，但是底层实质发送post调用服务端8001
    @GetMapping("/create")
    public CommonResult<Payment> create(Payment payment){
        //ForObject 返回对象为响应体中数据转化成的对象，基本上可以理解为Json
        //ForEntity 返回对象为ResponseEntity对象
        //    restTemplate.postForEntity(PaymentSrv_URL ,payment,CommonResult.class).getBody();
        return restTemplate.postForObject(PaymentSrv_URL + "/payment/create" , payment, CommonResult.class);
    }

    //编写根据id获取记录
    //客户端用浏览器是get请求，但是底层实质发送post调用服务端8001
    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable Long id){
        return restTemplate.getForObject(PaymentSrv_URL + "/payment/get/" + id,CommonResult.class,id);
    }

    //测试手写的负载均衡轮询算法(测试ok，但功能仅限于选择服务。如果要想像ribbon那样直接写服务别名就能访问还是不行。)
//    @GetMapping("/lb")
//    public String LbTest(){
//        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
//        ServiceInstance serviceInstance = lbRule.choose(instances);
//        URI uri = serviceInstance.getUri();
//        return restTemplate.getForObject(uri + "/payment/lb", String.class);
//    }
}
