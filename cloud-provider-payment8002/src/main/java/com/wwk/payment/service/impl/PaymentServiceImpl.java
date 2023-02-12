package com.wwk.payment.service.impl;

import com.wwk.payment.dao.PaymentDao;
import com.wwk.payment.service.PaymentService;
import com.wwk.springcloud.entities.Payment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wwkbear
 * @create 2023-01-27-17:33
 */
@Service
public class PaymentServiceImpl implements PaymentService {
//    @Autowired
    @Resource //这个也能自动注入
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
