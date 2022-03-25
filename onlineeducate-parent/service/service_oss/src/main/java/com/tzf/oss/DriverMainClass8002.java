package com.tzf.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

// 过滤掉DataSourceAutoConfiguration，不让他自动装配
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages ={ "com.tzf"})
@EnableDiscoveryClient
public class DriverMainClass8002 {
    public static void main(String[] args) {
        SpringApplication.run(DriverMainClass8002.class, args);
    }
}
