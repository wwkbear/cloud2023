package com.wwk.payment.service;

import com.wwk.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author wwkbear
 * @create 2023-01-27-17:29
 */

public interface PaymentService {
    public int create(Payment payment); //插入记录
    public Payment getPaymentById(@Param("id") Long id); //根据id查询记录
}
