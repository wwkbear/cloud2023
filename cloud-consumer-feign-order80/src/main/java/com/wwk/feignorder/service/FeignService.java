package com.wwk.feignorder.service;

import com.wwk.springcloud.entities.CommonResult;
import com.wwk.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wwkbear
 * @create 2023-02-08-17:43
 */
@Service
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface FeignService {
    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/payment/openlb")
    String openFeignTimeout();
}
