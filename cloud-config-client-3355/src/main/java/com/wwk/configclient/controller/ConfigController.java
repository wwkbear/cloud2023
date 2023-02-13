package com.wwk.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wwkbear
 * @create 2023-02-13-17:11
 */
@RestController
@RefreshScope
@RequestMapping("/config")
public class ConfigController {
//    @Value("${config.info}")
//    private String configInfo;
//
//    @GetMapping("/configinfo")
//    public String getConfigInfo(){
//        return configInfo;
//    }
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo()
    {
        return configInfo;
    }


}
