package com.tzf.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzf.commonutil.CourseWebVoOrder;
import com.tzf.commonutil.JwtUtils;
import com.tzf.commonutil.Resouce;
import com.tzf.eduservice.clients.OrderClients;
import com.tzf.eduservice.entity.EduCourse;
import com.tzf.eduservice.entity.vo.*;
import com.tzf.eduservice.service.EduChapterService;
import com.tzf.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Resource
    private EduCourseService eduCourseService;

    @Resource
    private OrderClients orderClients;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 保存课程基本信息
     *
     * @param courseInfo
     * @return
     */
    @DisableAuth
    @PostMapping("saveCourse")
    public Resouce saveCourse(@RequestBody CourseInfo courseInfo) {
        String id = eduCourseService.saveCourseInfo(courseInfo);
        return Resouce.ok().data("courseId", id);
    }

    /**
     * 根据id查询信息用于回显
     *
     * @param id
     * @return
     */
    @DisableAuth
    @GetMapping("getCourseById/{id}")
    public Resouce getCourseById(@PathVariable String id) {
        CourseInfo courseInfo = eduCourseService.getCourseById(id);
        return Resouce.ok().data("courseInfo", courseInfo);
    }

    /**
     * 修改课程更新
     * @param courseInfo
     * @return
     */
    @DisableAuth
    @PostMapping("updateCourse")
    public Resouce updateCourse(@RequestBody CourseInfo courseInfo) {
        eduCourseService.updateCourse(courseInfo);
        return Resouce.ok();
    }

    /**
     * 最终发布课程验证查看
     * @param id
     * @return
     */
    @DisableAuth
    @GetMapping("{id}")
    public Resouce getPublishCourseInfo(@PathVariable String id) {
        try {
            CoursePublishVo courseInfo = eduCourseService.getPublishCourseInfo(id);
            return Resouce.ok().data("list", courseInfo);
        } catch (Exception e) {
            return Resouce.error();
        }

    }

    /**
     * 发布
     * @param id
     * @return
     */
    @DisableAuth
    @PostMapping("publishCourse/{id}")
    public Resouce publishCourse(@PathVariable String id) {
        try {
            EduCourse eduCourse = new EduCourse();
            eduCourse.setId(id);
            eduCourse.setStatus("Normal");//设置课程发布状态
            eduCourseService.updateById(eduCourse);

            String count = redisTemplate.opsForValue().get("coursecount");
            if (count == null || count == "") {
                redisTemplate.opsForValue().set("coursecount", "1");
            }else{
                redisTemplate.opsForValue().increment("coursecount");
            }
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.ok();
        }
    }

    /**
     * 删除课程以及和其相关的信息
     *
     */
    @DisableAuth
    @DeleteMapping("removeCourse/{courseId}")
    public Resouce removeCourseById(@PathVariable String courseId) {
        try {
            eduCourseService.removeCourse(courseId);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }

    }

    /**
     * 课程分页表单
     * @param pageNum
     * @param pageSize
     * @param courseVO
     * @return
     */
    @DisableAuth
    @PostMapping("{pageNum}/{pageSize}")
    public Resouce pageCourse(
            @PathVariable("pageNum") Long pageNum,
            @PathVariable("pageSize") Long pageSize,
            @RequestBody(required = false) CourseVO courseVO) {
        // 创建分页对象


        Page<EduCourse> page =   eduCourseService.getCoursePage(pageNum, pageSize, courseVO);
        // 一共多少条记录
        long total = page.getTotal();

        // 获取分页后的数据
        List<EduCourse> records = page.getRecords();
        return Resouce.ok().data("total", total).data("records", records);
    }
//===================客户端显示========================
    /**
     * 获取首页显示的热门课程
     * @return
     */
    @DisableAuth
    @GetMapping("getCourseLimit")
    public Resouce getCourseLimit(){
        try {
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("gmt_modified");
            wrapper.last("limit 8");
            List<EduCourse> list = eduCourseService.list(wrapper);
            return Resouce.ok().data("courseList", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }

    /**
     * 条件查询课程
     * @param pageNum
     * @param pageSize
     * @param courseInfoVO
     * @return
     */
    @PostMapping("getCourseAll/{pageNum}/{pageSize}")
    public Resouce getCourseAll(
            @PathVariable("pageNum") Long pageNum,
            @PathVariable("pageSize") Long pageSize,
            @RequestBody(required = false) CourseInfoVO courseInfoVO
    ) {

        Page<EduCourse> page = eduCourseService.getCourseAll(pageNum, pageSize, courseInfoVO);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return Resouce.ok().data("total", total).data("records", records);
    }

    @Autowired
    private EduChapterService eduChapterService;

//   @DisableAuth
    @PostMapping("getCourseDataById/{id}")
    public Resouce getCourseDataById(@PathVariable String id) {

        // 获取课程信息
        CourseDataVO courseDataVO = eduCourseService.getCourseDataById(id);

        // 获取章节信息
        List<ChapterVo> chapterVoList = eduChapterService.getEduChapterVOById(id);
        return Resouce.ok().data("courseDataVO", courseDataVO).data("chapterVoList", chapterVoList);
    }


    //根据课程id查询课程信息
    @DisableAuth
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseDataVO courseDataById = eduCourseService.getCourseDataById(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseDataById,courseWebVoOrder);
        return courseWebVoOrder;
    }

    /**
     * 获取token
     * @param id
     * @param request
     * @return
     */
    @DisableAuth
    @GetMapping("isBuyByCourseId/{id}")
    public Resouce getCourseDataById(@PathVariable String id, HttpServletRequest request) {
        String token = JwtUtils.getMemberIdByJwtToken(request);
        if (token == null || token == "") {
            token = "1";
        }
        Boolean buy = orderClients.isBuy(id, token);
        return Resouce.ok().data("isBuy", buy);
    }

}

