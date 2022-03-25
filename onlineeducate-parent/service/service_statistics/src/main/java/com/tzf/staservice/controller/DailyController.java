package com.tzf.staservice.controller;


import com.tzf.commonutil.Resouce;
import com.tzf.servicebase.config.DisableAuth;
import com.tzf.staservice.clients.RegisterClient;
import com.tzf.staservice.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/staservice/daily")
@CrossOrigin
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @Autowired
    private RegisterClient registerClient;

    /**
     * 测试   统计某一天注册的人数
     * @param day
     * @return
     */
    @GetMapping("getRegisterCount/{day}")
    public Resouce ee(@PathVariable("day") String day) {
        Integer count = registerClient.registerCount(day);
        return Resouce.ok().data("count", count);
    }

    /**
     * 添加查询统计结果
     *
     * @return
     */
    @DisableAuth
    @PostMapping("getCountByDay/{type}/{begin}/{end}")
    public Resouce getCountByDay(
            @PathVariable("type") String type,
            @PathVariable("begin") String begin,
            @PathVariable("end") String end) {
        Map<String, Object> map = dailyService.getCountByDay(type, begin, end);
        return Resouce.ok().data(map);
    }

    @DisableAuth
    @PostMapping("getDefaultDate")
    public Resouce getDefaultDate(){
        Map<String, Object> map = dailyService.getDefaultDate();
        return Resouce.ok().data(map);
    }
}

