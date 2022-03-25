package com.tzf.sms.controller;

import com.tzf.commonutil.Resouce;
import com.tzf.servicebase.config.DisableAuth;
import com.tzf.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/serviceSms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @DisableAuth
    @PostMapping("sendCode/{phone}")
    public Resouce sendCode1(@PathVariable("phone") String phone) {
        try {

            smsService.sendCodeByPhone(phone);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }
}
