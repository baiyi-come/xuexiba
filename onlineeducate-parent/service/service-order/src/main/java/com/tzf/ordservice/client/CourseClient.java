package com.tzf.ordservice.client;

import com.tzf.commonutil.CourseWebVoOrder;
import com.tzf.commonutil.Resouce;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "service-edu")
public interface CourseClient {
    @PostMapping("/eduservice/course/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) ;

}
