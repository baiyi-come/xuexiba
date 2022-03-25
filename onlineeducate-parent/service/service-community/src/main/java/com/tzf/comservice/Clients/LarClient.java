package com.tzf.comservice.Clients;

import com.tzf.larservice.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-lar")
public interface LarClient {

    @PostMapping("/larservice/ucenter/getUcenterMember/{id}")
    public UcenterMember getUcenterMember(@PathVariable("id") String id);
}
