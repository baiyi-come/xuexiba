package com.tzf.eduservice.clients;

import org.springframework.stereotype.Component;

@Component
public class OrderClientsHystrix implements OrderClients{
    @Override
    public Boolean isBuy(String courseId, String userId) {
        return false;
    }
}
