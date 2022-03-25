package com.tzf.staservice.utils;

import com.tzf.servicebase.config.DisableAuth;
import com.tzf.staservice.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private DailyService dailyService;

//    @DisableAuth
//    @Scheduled(cron = "10 * * * * ? ")
//    public void test() {
//        dailyService.getRegisterCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
//    }

    // 每晚0点统计前一天的数据，存入数据库
    @DisableAuth
    @Scheduled(cron = "0 0 0 * * ?")
    public void SaveDayCount() {

        dailyService.getRegisterCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));

    }
}
