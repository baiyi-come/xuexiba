package com.tzf.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import com.tzf.staservice.clients.RegisterClient;
import com.tzf.staservice.entity.Daily;
import com.tzf.staservice.mapper.DailyMapper;
import com.tzf.staservice.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private RegisterClient registerClient;

    @Autowired
    private StringRedisTemplate redisTemplate;
    // 统计各个访问人数，存入数据
    @Override
    public void getRegisterCount(String day) {
        // 判断数据库中是否有此日期数据，有则删除
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        Integer count = baseMapper.selectCount(wrapper);
        if (count != 0) {
            baseMapper.delete(wrapper);
        }

        // 添加统计数据
        Daily daily = new Daily();

        Integer registerCount = registerClient.registerCount(day);

        // 添加注册人数
        daily.setRegisterNum(registerCount);
        // 添加统计日期
        daily.setDateCalculated(day);

        // 统计当天有多少人登录
        String logincount = redisTemplate.opsForValue().get("logincount");

        if (logincount == null||logincount=="") {
            daily.setLoginNum(0);
        }else {
            // 统计完成。删除redis中当天数据
            try {
                daily.setLoginNum(Integer.valueOf(logincount));
                redisTemplate.delete("logincount");
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalCommonExceptionHandler(20001, "系统错误，抢修中");
            }
        }

        // 从redis中获取播放数据
        String videocount = redisTemplate.opsForValue().get("videocount");
            if (videocount==null||videocount==""){
                daily.setVideoViewNum(0);
            }else {
                try {
                    daily.setVideoViewNum(Integer.valueOf(videocount));
                    redisTemplate.delete("videocount");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new GlobalCommonExceptionHandler(20001, "系统错误，抢修中");
                }
            }

        // 新增课程数量
        String coursecount = redisTemplate.opsForValue().get("coursecount");

      if (coursecount==null||coursecount==""){
          daily.setCourseNum(0);
      }else{
        try {
            daily.setCourseNum(Integer.valueOf(coursecount));
            redisTemplate.delete("coursecount");
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalCommonExceptionHandler(20001, "系统错误，抢修中");
        }
      }
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getCountByDay(String type, String begin, String end) {

        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        // 选择查询哪一列  日期和数量
        wrapper.select("date_calculated",type);

        // 选择查询时间段
        wrapper.between("date_calculated", begin, end);

        List<Daily> list = baseMapper.selectList(wrapper);

        ArrayList<String> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();

        // 遍历list，分成x,y轴数据
        for (Daily daily : list) {
            // 获取日期信息
            String calculated = daily.getDateCalculated();
            // 存入x轴
            x.add(calculated);

            // 获取访问量信息
            // 判断查询的是什么类型的访问量
            switch (type) {
                case "login_num":
                    y.add(daily.getLoginNum());
                    break;
                case "register_num":
                    y.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    y.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    y.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        // 创建map集合
        HashMap<String, Object> map = new HashMap<>();
        // 把x,y轴数据存入其中，返回前端，显示
        map.put("x", x);
        map.put("y", y);
        return map;
    }

    @Override
    public Map<String, Object> getDefaultDate() {
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("date_calculated");
        wrapper.last("limit 6");

        List<Daily> list = baseMapper.selectList(wrapper);
        ArrayList<String> x = new ArrayList<>();
        Map<String,Object> y = new HashMap<>();
        ArrayList<Object> login = new ArrayList<>();
        ArrayList<Object> register = new ArrayList<>();
        ArrayList<Object> video = new ArrayList<>();
        ArrayList<Object> course = new ArrayList<>();
        x.add("今日数据明天更新");
        login.add("登录人数");
        register.add("注册人数");
        video.add("观看人数");
        course.add("课程上新");
        // 遍历list，分成x,y轴数据
        for (Daily daily : list) {
            // 获取日期信息
            String calculated = daily.getDateCalculated();
            // 存入x轴
            x.add(calculated);
            // 获取访问量信息
            login.add(daily.getLoginNum());
            register.add(daily.getRegisterNum());
            video.add(daily.getVideoViewNum());
            course.add(daily.getCourseNum());
        }
        y.put("login", login);
        y.put("register",register);
        y.put("video",video);
        y.put("course",course);
        // 创建map集合
        HashMap<String, Object> map = new HashMap<>();
        // 把x,y轴数据存入其中，返回前端，显示
        map.put("x", x);
        map.put("y", y);
        return map;
    }
}
