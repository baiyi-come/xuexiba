package com.tzf.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages ={ "com.tzf"})
public class DriverMainClass8001 {

    public static void main(String[] args) {
        SpringApplication.run(DriverMainClass8001.class, args);
    }
}
