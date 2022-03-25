package com.tzf.ordservice.client;

import com.tzf.commonutil.Resouce;
import com.tzf.commonutil.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-lar")
public interface UserClient {
    @PostMapping("/larservice/ucenter/getUserById/{id}")
    public UcenterMemberOrder getUserById(@PathVariable("id") String id);
}
