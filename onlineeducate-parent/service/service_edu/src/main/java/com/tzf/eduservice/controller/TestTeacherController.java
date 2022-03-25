package com.tzf.eduservice.controller;

import com.tzf.commonutil.Resouce;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class TestTeacherController {

    // login
    @DisableAuth
    @PostMapping("login")
    public Resouce login() {
        return Resouce.ok().data("token", "admin");
    }
    @DisableAuth
    @GetMapping("info")
    public Resouce info(){
        return Resouce.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "http://aliyun_id_photo_bucket.oss.aliyuncs.com/default_handsome.jpg");
    }
}
