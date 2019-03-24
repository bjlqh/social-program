package com.tensquare.spit;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
@EnableEurekaClient
public class SpitApplicaiton {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpitApplicaiton.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Bean
    public IdWorker getIdworker() {
        return new IdWorker(1, 1);
    }
}
