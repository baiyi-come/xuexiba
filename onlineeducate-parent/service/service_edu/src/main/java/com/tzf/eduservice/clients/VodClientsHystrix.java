package com.tzf.eduservice.clients;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tzf.commonutil.Resouce;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * VodClients的熔断设置
 */
@Component
public class VodClientsHystrix implements VodClients {
    @Override
    public Resouce removeVideo(String id) {
        return Resouce.error().message("服务崩溃了，正在紧急修复中/(ㄒoㄒ)/~~");
    }

    @Override
    public Resouce removeVideoBatch(List<String> videoIdList) {
        return Resouce.error().message("服务崩溃了，正在紧急修复中/(ㄒoㄒ)/~~");
    }
}
