package com.wwk.getway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 在网关层手动编写配置类，将一些特别的操作在这实现。
 * @author wwkbear
 * @create 2023-02-12-15:09
 */
@Component
public class GetWayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder)
    {
        RouteLocatorBuilder.Builder routes = builder.routes();

        routes.route("path_route_wwk", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();

        return routes.build();

    }
    @Bean
    public RouteLocator customRouteLocator2(RouteLocatorBuilder builder)
    {
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("path_route_wwk2", r -> r.path("/v/music/").uri("https://www.bilibili.com/v/music/?spm_id_from=333.1007.0.0")).build();
        return routes.build();
    }
}
