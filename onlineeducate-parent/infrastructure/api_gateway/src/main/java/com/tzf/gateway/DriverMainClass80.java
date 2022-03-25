package com.tzf.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.*;

@SpringBootApplication
@EnableDiscoveryClient
public class DriverMainClass80 {
    public static void main(String[] args) {
        SpringApplication.run(DriverMainClass80.class, args);
    }
}
