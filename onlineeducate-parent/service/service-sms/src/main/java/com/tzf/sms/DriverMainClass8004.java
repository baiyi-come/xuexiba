package com.tzf.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.tzf")
public class DriverMainClass8004 {
    public static void main(String[] args) {
        SpringApplication.run(DriverMainClass8004.class, args);
    }
}
