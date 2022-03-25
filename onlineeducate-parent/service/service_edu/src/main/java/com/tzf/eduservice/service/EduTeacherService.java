package com.tzf.eduservice.service;

import com.tzf.eduservice.entity.EduTeacher;
import com.tzf.eduservice.entity.vo.TeacherQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Page<EduTeacher> getTeacherPage(Long pageNum, Long pageSize, TeacherQueryVO teacherQueryVO);

    Page<EduTeacher> getTeacherList(Long pageNUm, Long pageSize, TeacherQueryVO teacherQueryVO);
}
