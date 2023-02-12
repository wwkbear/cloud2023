package com.wwk.payment.controller;

import com.wwk.payment.service.PaymentService;
import com.wwk.springcloud.entities.CommonResult;
import com.wwk.springcloud.entities.Payment;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wwkbear
 * @create 2023-01-27-17:38
 */
@RestController
@RequestMapping("/payment")
@Log4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
//        log.info("插入结果：{}"+result);
        if (result > 0) {
            return new CommonResult(200,"插入成功，返回结果" + result + "\t 服务端口：" + serverPort,payment);
        }else {
            return new CommonResult(400,"插入失败",null);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult getPaymentByid(@PathVariable("id") Long id){
        Payment paymentById = paymentService.getPaymentById(id);
//        log.info("查询结果:{}"+ paymentById);
        if (paymentById != null) {
            return new CommonResult(200,"查询成功" + "\t 服务端口：" + serverPort,paymentById);
        }else {
            return new CommonResult(400,"没有查到对应记录，查询id：" + id,null);
        }
    }

    @GetMapping("/discovery")
    public Object discovery(){
        //获取全部注册的服务器列表
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info(service);
            System.out.println(service);
        }
        //获取服务名称对应下的全部服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            //instance对应着具体一个服务模块，那么就能获取到对应的实例名称，本机地址，端口号，url等信息。
            log.info(instance.getInstanceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri() );
            System.out.println(instance.getInstanceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }

        return this.discoveryClient;
    }

    @GetMapping("/lb")
    public String lbTest(){
        return serverPort;
    }


    @GetMapping("/openlb")
    public String openFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return serverPort;
    }
}
