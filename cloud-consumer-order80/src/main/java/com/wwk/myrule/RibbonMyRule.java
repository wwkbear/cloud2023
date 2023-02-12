package com.wwk.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wwkbear
 * @create 2023-02-07-19:47
 */
@Configuration
public class RibbonMyRule {
    @Bean
    public IRule myRule(){
        return new RandomRule(); //定义为随机
    }
}
