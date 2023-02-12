package com.wwk.payment.dao;

import com.wwk.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 支付记录
 * @author wwkbear
 * @create 2023-01-27-17:20
 */
@Mapper
public interface PaymentDao {
    public int create(Payment payment); //插入记录
    public Payment getPaymentById(@Param("id") Long id); //根据id查询记录
}
