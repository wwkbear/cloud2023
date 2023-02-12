package com.wwk.order.service.impl;

import com.wwk.order.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author wwkbear
 * @create 2023-02-11-16:20
 */
@Service
public class OrderFallbackServiceImpl implements OrderService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "服务调用失败，提示来自：cloud-consumer-feign-order80";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "服务调用失败，提示来自：cloud-consumer-feign-order80";
    }
}
