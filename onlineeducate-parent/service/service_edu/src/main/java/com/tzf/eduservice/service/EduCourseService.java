package com.tzf.eduservice.service;

import com.tzf.eduservice.entity.EduCourse;
import com.tzf.eduservice.entity.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseById(String id);

    void updateCourse(CourseInfo courseInfo);

    CoursePublishVo getPublishCourseInfo(String id);


    Page<EduCourse> getCoursePage(Long pageNum, Long pageSize, CourseVO courseVO);

    void removeCourse(String courseId);

    List<EduCourse> getCourseByTeacherId(Long id);

    Page<EduCourse>getCourseAll(Long pageNum, Long pageSize, CourseInfoVO courseInfoVO);

    CourseDataVO getCourseDataById(String id);
}
