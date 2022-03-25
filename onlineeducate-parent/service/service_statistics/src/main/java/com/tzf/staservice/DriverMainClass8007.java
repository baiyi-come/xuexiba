package com.tzf.staservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling // 定时发送消息
@ComponentScan("com.tzf")
public class DriverMainClass8007 {
    public static void main(String[] args) {
        SpringApplication.run(DriverMainClass8007.class, args);
    }
}
