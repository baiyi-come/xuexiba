package com.tzf.eduservice.controller;


import com.tzf.commonutil.Resouce;
import com.tzf.eduservice.entity.subject.OneSubject;
import com.tzf.eduservice.service.EduSubjectService;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 导入数据
     * @param file
     * @return
     */
    @DisableAuth
    @PostMapping("saveSubject")
    public Resouce saveSubject(MultipartFile file) {
        try {
            eduSubjectService.saveSubject(file, eduSubjectService);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error().code(20001).message("上传错误，请检查文件是否规范");
        }
    }

    @DisableAuth
    @GetMapping("getAllSubject")
    public Resouce getSubjectList(){
        List<OneSubject> list = eduSubjectService.getSubjectList();
        return Resouce.ok().data("list", list);
    }

    /**
     * 导出数据
     * @return
     */
    @DisableAuth
    @GetMapping("exportData")
    public Resouce exportData(HttpServletResponse response) {

        eduSubjectService.exportData(response);
        return Resouce.ok();
    }
}

