package com.tzf.eduservice.service.impl;

import com.tzf.eduservice.entity.EduTeacher;
import com.tzf.eduservice.entity.vo.TeacherQueryVO;
import com.tzf.eduservice.mapper.EduTeacherMapper;
import com.tzf.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Page<EduTeacher> getTeacherPage(Long pageNum, Long pageSize, TeacherQueryVO teacherQueryVO) {
        Page<EduTeacher> teacherPage = new Page<>(pageNum, pageSize);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String begin = teacherQueryVO.getBegin();
        String end = teacherQueryVO.getEnd();
        Integer level = teacherQueryVO.getLevel();
        String name = teacherQueryVO.getName();
        wrapper.orderByDesc("gmt_create");
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        this.page(teacherPage, wrapper);
        return teacherPage;
    }

    @Override
    public Page<EduTeacher> getTeacherList(Long pageNUm, Long pageSize, TeacherQueryVO teacherQueryVO) {
        // 分页
        Page<EduTeacher> page = new Page<>(pageNUm, pageSize);
        // 添加条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQueryVO.getName();
        // 若有条件，查询返回
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
            this.page(page, wrapper);
            return page;
        }
        // 无条件查询返回
        this.page(page, null);
        return page;
    }
}
