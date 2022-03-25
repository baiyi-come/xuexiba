package com.tzf.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzf.commonutil.Resouce;
import com.tzf.eduservice.entity.EduCourse;
import com.tzf.eduservice.entity.EduTeacher;
import com.tzf.eduservice.entity.vo.TeacherQueryVO;
import com.tzf.eduservice.service.EduCourseService;
import com.tzf.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzf.servicebase.config.DisableAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    @Resource

    private EduCourseService eduCourseService;

    @ApiOperation("教师查询")
    @DisableAuth
    @GetMapping("/getEduTeacherAll")
    public Resouce getEduTeacherAll() {

        List<EduTeacher> list = eduTeacherService.list(null);

        return Resouce.ok().data("list", list);
    }

    @ApiOperation("教师删除")
    @DeleteMapping("{id}")
    @DisableAuth
    public Resouce removeEduTeacherbyId(
            @ApiParam(name = "id", value = "教师id", required = true)
            @PathVariable("id") String id) {

        boolean b = eduTeacherService.removeById(id);
        if (b == true) {
            return Resouce.ok();
        } else {
            return Resouce.error();
        }
    }

    @ApiOperation("无条件分页查询")
    @GetMapping("{pageNum}/{pageSize}")
    @DisableAuth
    public Resouce pageTeacher(
            @PathVariable("pageNum") Long pageNum,
            @PathVariable("pageSize") Long pageSize) {
        // 创建分页对象
        Page<EduTeacher> page = new Page<>(pageNum, pageSize);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        // 进行分页
        eduTeacherService.page(page, null);
        // 一共多少条记录
        long total = page.getTotal();

        // 获取分页后的数据
        List<EduTeacher> records = page.getRecords();
        return Resouce.ok().data("total", total).data("records", records);
    }

    @ApiOperation("条件分页查询")
    @PostMapping("get/{pageNum}/{pageSize}")
    @DisableAuth
    public Resouce getTeacherPage(
            @PathVariable Long pageNum,
            @PathVariable Long pageSize,
            @RequestBody(required = false) TeacherQueryVO teacherQueryVO
    ) {
        Page<EduTeacher> teacherPage = eduTeacherService.getTeacherPage(pageNum, pageSize, teacherQueryVO);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return Resouce.ok().data("total", total).data("records", records);
    }

    @ApiOperation("添加教师")
    @PostMapping("addTeacher")
    @DisableAuth
    public Resouce addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save == true) {
            return Resouce.ok();
        } else {
            return Resouce.error();
        }
    }


    @ApiOperation("查找教师")
    @GetMapping("{id}")
    @DisableAuth
    public Resouce findTeacher(@PathVariable Long id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return Resouce.ok().data("teacher", teacher);
    }

    @ApiOperation("更新教师")
    @PostMapping("updateTeacher")
    @DisableAuth
    public Resouce updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b == true) {
            return Resouce.ok();
        } else {
            return Resouce.error();
        }
    }

//====================客户端=========================

    /**
     * 显示前8位老师
     *
     * @return
     */
    @DisableAuth
    @GetMapping("getTeacherLimit")
    public Resouce getTeacherLimit() {

        try {
            QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("gmt_modified");
            wrapper.last("limit 8");
            List<EduTeacher> list = eduTeacherService.list(wrapper);
            return Resouce.ok().data("teacherList", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }

    /**
     * 名师界面
     *
     * @param pageNUm
     * @param pageSize
     * @param teacherQueryVO
     * @return
     */
    @PostMapping("getTeacher/{pageNum}/{pageSize}")
    public Resouce getTeacherList(@PathVariable("pageNum") Long pageNUm,
                                  @PathVariable("pageSize") Long pageSize,
                                  @RequestBody(required = false) TeacherQueryVO teacherQueryVO
    ) {
        try {
            Page<EduTeacher> teacherPage = eduTeacherService.getTeacherList(pageNUm, pageSize, teacherQueryVO);
            long total = teacherPage.getTotal();
            List<EduTeacher> records = teacherPage.getRecords();
            return Resouce.ok().data("total", total).data("records", records);
        } catch (Exception e) {
            return Resouce.error();
        }
    }

    /**
     * 获取单个教师信息
     * @param id
     * @return
     */
    @PostMapping("getTeacherDate/{id}")
//  @DisableAuth
    public Resouce getTeacherDate(@PathVariable Long id) {
        try {
            EduTeacher teacher = eduTeacherService.getById(id);
            List<EduCourse> list = eduCourseService.getCourseByTeacherId(id);
            return Resouce.ok().data("teacher", teacher).data("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }

    }



}

