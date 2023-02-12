package com.wwk.getway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author wwkbear
 * @create 2023-02-12-15:46
 */
@Component
public class MyLogGetWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(new Date() + "进入自定义filter过滤器");
        String vipname = exchange.getRequest().getQueryParams().getFirst("vipname");
        if (vipname == null){
            System.out.println("用户名为null，无法登录");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE); //设置响应 状态码为不接受请求
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange); //如果能走到这步，则完成该过滤器的任务。将控制权转交给下一个过滤器。
    }

    /**
     * 优先级  -2的31次方 ~ 2的31次方-1 就是Integer范围
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
