package com.tzf.eduservice.clients;

import com.tzf.larservice.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-lar")
@Component
public interface UcenterClients {
    @PostMapping("/larservice/ucenter/getUcenterMember/{id}")
    public UcenterMember getUcenterMember(@PathVariable("id") String id);
}
