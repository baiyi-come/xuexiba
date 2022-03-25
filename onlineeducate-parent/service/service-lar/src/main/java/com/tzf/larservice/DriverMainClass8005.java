package com.tzf.larservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.tzf")
public class DriverMainClass8005 {
    public static void main(String[] args) {
        SpringApplication.run(DriverMainClass8005.class, args);
    }
}
