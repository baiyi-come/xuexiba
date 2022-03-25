package com.tzf.sms.service.impl;

import com.tzf.commonutil.Resouce;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import com.tzf.sms.entity.Sms;
import com.tzf.sms.service.SmsService;
import com.tzf.sms.utils.sendCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private Sms sms;


    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 发送验证码
     * @param phone
     */
    @Override
    public void sendCodeByPhone(String phone) {

        // 调用方法生成验证码
        Resouce resouce = sendCode.sendMessage(sms.getHost(), sms.getPath(), sms.getMethod(), sms.getAppcode(), phone, sms.getSmsSignId(), sms.getTemplateId());

        // 获取验证码
        String code=(String) resouce.getData().get("code");
        try {
            // 存入redis临时缓存,有效时间5分钟
            ValueOperations<String, String> forValue = redisTemplate.opsForValue();
            forValue.set(phone, code, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
          throw new GlobalCommonExceptionHandler(20001,"验证码出错了/(ㄒoㄒ)/~~");
        }
    }
}
