package com.wwk.order.lber;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author wwkbear
 * @create 2023-02-08-15:11
 */
public interface LbRule {
    ServiceInstance choose(List<ServiceInstance> serviceInstances);
}
