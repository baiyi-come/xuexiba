package com.tzf.staservice.service;

import com.tzf.staservice.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 */
public interface DailyService extends IService<Daily> {

    public void getRegisterCount(String day);

    Map<String, Object> getCountByDay(String type, String begin, String end);

    Map<String, Object> getDefaultDate();
}
