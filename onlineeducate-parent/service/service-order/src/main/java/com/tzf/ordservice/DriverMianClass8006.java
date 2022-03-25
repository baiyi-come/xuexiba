package com.tzf.ordservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableTransactionManagement
@ComponentScan("com.tzf")
public class DriverMianClass8006 {
    public static void main(String[] args) {
        SpringApplication.run(DriverMianClass8006.class, args);
    }
}
