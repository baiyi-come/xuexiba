package com.tzf.oss.controller;

import com.tzf.oss.service.OssService;
import com.tzf.commonutil.Resouce;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @DisableAuth
    @PostMapping
    public Resouce uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFile(file);
//        System.out.println(url);
        return Resouce.ok().data("url",url);
    }
}
