package com.tzf.eduservice.mapper;

import com.tzf.eduservice.entity.EduCourse;
import com.tzf.eduservice.entity.vo.CourseDataVO;
import com.tzf.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublishCourseInfo(String id);

    CourseDataVO getBaseCourseInfo(String id);
}
