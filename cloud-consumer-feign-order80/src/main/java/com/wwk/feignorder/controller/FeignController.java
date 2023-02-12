package com.wwk.feignorder.controller;

import com.wwk.feignorder.service.FeignService;
import com.wwk.springcloud.entities.CommonResult;
import com.wwk.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author wwkbear
 * @create 2023-02-08-17:48
 */
@RestController
@RequestMapping("/consumer/payment")
public class FeignController {
    @Autowired
    private FeignService feignService;

    @GetMapping(value = "/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return feignService.getPaymentById(id);
    }

    @GetMapping("/openlb")
    public String openFeign(){
        //openFeign底层有使用Ribbon 而Ribbon客户端默认等待1秒，超时则报错。
        //如果后端业务侧确实需要较长时间处理，那么就得手动修改等待时间。在配置文件application中修改。
        return feignService.openFeignTimeout();
    }
}
