package com.wwk.payment.controller;

import com.wwk.payment.service.PaymentService;
import com.wwk.springcloud.entities.CommonResult;
import com.wwk.springcloud.entities.Payment;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping("/lb")
    public String lbTest(){
        return serverPort;
    }
}
