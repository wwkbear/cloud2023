package com.wwk.order.lber;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wwkbear
 * @create 2023-02-08-15:12
 */
//@Component
public class LbRuleImpl implements LbRule{
    //int包装类的 原子类
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    /*
        思路：
            0.要考虑并发问题（考虑锁问题，使用轻量级锁：自旋锁，使用到的类：Integer原子类 ）
            1.选择具体的服务器的方法
            2.计算具体list下标 得到服务
         */

    /**
     * 根据访问次数对其进行取模轮询
     * @param serviceInstances 注册中心中服务在线的服务器列表
     * @return
     */
    @Override
    public final ServiceInstance choose(List<ServiceInstance> serviceInstances) {
        //先判断列表是否为空
        if (serviceInstances == null || serviceInstances.size() <= 0){
            return null;
        }
        //不为空则选择合适的服务
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }

    /**
     * 更新访问次数
     * 考虑并发问题使用自旋锁cas
     * @return
     */
    public int getAndIncrement(){
        int current;
        int next;
        do {
            //做个简单的轮询负载均衡，实现轮询使用加1的办法。如何循环？使用取模的办法实现循环list。
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("访问次数" + next);
        return next;
    }

}
