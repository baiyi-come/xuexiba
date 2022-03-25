package com.tzf.eduservice.clients;

import com.tzf.commonutil.Resouce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@FeignClient(value = "service-order",fallback = OrderClientsHystrix.class)
@Component
public interface OrderClients {
    @GetMapping("/ordservice/order/isBuy/{courseId}/{memberId}")
    public Boolean isBuy(
            @PathVariable("courseId") String courseId,
            @PathVariable("memberId") String memberId);
}
