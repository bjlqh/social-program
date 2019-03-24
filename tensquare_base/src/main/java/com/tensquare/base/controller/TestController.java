package com.tensquare.base.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//自定义配置，用来刷新配置
@RequestMapping("/test")
public class TestController {

    @Value("${sms.ip}")
    private String ip;

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public String ip() {
        System.out.println(ip);
        return ip;
    }
}
