package com.tzf.eduservice.clients;

import com.tzf.commonutil.Resouce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodClientsHystrix.class)
@Component
public interface VodClients {
    /**
     * 单独删除视频
     * @param id
     * @return
     */
    @DeleteMapping("/serviceVod/removeVideo/{id}")
    public Resouce removeVideo(@PathVariable("id") String id);

    /**
     * 批量删除视频
     *
     * @param videoIdList
     * @return
     */
    @DeleteMapping("/serviceVod/removeVideoBatch")
    public Resouce removeVideoBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
