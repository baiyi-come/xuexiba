package com.tzf.staservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-lar")
public interface RegisterClient {
    /**
     * 获取当天注册人数
     * @param day
     * @return
     */
    @GetMapping("/larservice/ucenter/registerCount/{day}")
    public Integer registerCount(@PathVariable("day") String day);
}
