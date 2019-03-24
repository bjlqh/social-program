package com.tensquare.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;

@SpringBootApplication
@EnableEurekaClient //开启eureka客户端的支持，可以不写
@EnableFeignClients//开启fegin客户端的支持
@EnableDiscoveryClient//开启fegin的发现客户端
public class QAApplication {

    public static void main(String[] args) {
        SpringApplication.run(QAApplication.class, args);
    }

    @Bean
    public IdWorker idWorkker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public JwtUtil getJwtUtil() {
        return new util.JwtUtil();
    }
}
