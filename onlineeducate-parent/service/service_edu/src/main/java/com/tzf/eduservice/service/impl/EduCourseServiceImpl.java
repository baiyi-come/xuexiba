package com.tzf.eduservice.service.impl;

import com.tzf.eduservice.clients.VodClients;
import com.tzf.eduservice.entity.EduChapter;
import com.tzf.eduservice.entity.EduCourse;
import com.tzf.eduservice.entity.EduCourseDescription;
import com.tzf.eduservice.entity.EduVideo;
import com.tzf.eduservice.entity.vo.*;
import com.tzf.eduservice.mapper.EduCourseMapper;
import com.tzf.eduservice.service.EduChapterService;
import com.tzf.eduservice.service.EduCourseDescriptionService;
import com.tzf.eduservice.service.EduCourseService;
import com.tzf.eduservice.service.EduVideoService;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private VodClients vodClients;
    @Override
    public String saveCourseInfo(CourseInfo courseInfo) {

        // 判断courseInfo是否为空
        if (courseInfo == null) {
            throw new GlobalCommonExceptionHandler(20001, "课程数据为空,请检查");
        }

        EduCourse eduCourse = new EduCourse();

        // 把courseInfo的值复制给eduCourse
        BeanUtils.copyProperties(courseInfo, eduCourse);

        int insert = baseMapper.insert(eduCourse);
        // 判断是否插入成功
        if (insert <= 0) {
            throw new GlobalCommonExceptionHandler(20001, "添加失败了o(╥﹏╥)o");
        }

        // 获取插入成功的数据id
        String eduCourseId = eduCourse.getId();

        EduCourseDescription courseDescription = new EduCourseDescription();

        courseDescription.setDescription(courseInfo.getDescription());
        // 保证课程描述与课程一致
        courseDescription.setId(eduCourseId);

        eduCourseDescriptionService.save(courseDescription);

        return eduCourseId;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public CourseInfo getCourseById(String id) {

        // 查询课程信息
        EduCourse eduCourse = baseMapper.selectById(id);
        // 创建VO对象，负责传递数据到前端
        CourseInfo courseInfo = new CourseInfo();
        // 复制到VO对象
        BeanUtils.copyProperties(eduCourse, courseInfo);
        // 查询课程描述信息
        EduCourseDescription description = eduCourseDescriptionService.getById(id);

        BeanUtils.copyProperties(description, courseInfo);

        return courseInfo;
    }

    /**
     * 更新
     * @param courseInfo
     */
    @Override
    public void updateCourse(CourseInfo courseInfo) {
        // 从courseInfo 中分离出educourse进行baocun
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int updateById = baseMapper.updateById(eduCourse);
        // 判断是否保存成功
        if (updateById <= 0) {
            // 保存失败， 报错
            throw new GlobalCommonExceptionHandler(20001, "课程信息更新失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        // 设置课程描述
        description.setDescription(courseInfo.getDescription());
        // 设置id,用于更新
        description.setId(courseInfo.getId());
        boolean b = eduCourseDescriptionService.updateById(description);
        if (b == false) {
            throw new GlobalCommonExceptionHandler(20001, "课程描述更新失败");
        }
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public Page<EduCourse> getCoursePage(Long pageNum, Long pageSize, CourseVO courseVO) {
        Page<EduCourse> page = new Page<>(pageNum, pageSize);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modified" );
        String title = courseVO.getTitle();

        String status = courseVO.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        // 进行分页
        this.page(page, wrapper);
        return page;
    }

    @Override
    public void removeCourse(String courseId) {
        // 删除课程描述
        QueryWrapper<EduCourseDescription> descriptionQueryWrapper = new QueryWrapper<>();
        descriptionQueryWrapper.eq("id", courseId);
        boolean remove = eduCourseDescriptionService.remove(descriptionQueryWrapper);
        if ("false".equals(remove)) {
            throw new GlobalCommonExceptionHandler(20001, "删除失败");
        }

        // 删除章节和小节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);

        boolean remove1 = eduChapterService.remove(chapterQueryWrapper);
        if ("false".equals(remove1)) {
            throw new GlobalCommonExceptionHandler(20001, "删除失败");
        }
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            // 不为空加入数组
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }
        if (videoIds.size() > 0) {

            vodClients.removeVideoBatch(videoIds);
        }
        boolean remove2 = eduVideoService.remove(videoQueryWrapper);
        if ("false".equals(remove2)) {
            throw new GlobalCommonExceptionHandler(20001, "删除失败");
        }

        // 删除课程基本信息
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.eq("id", courseId);
        int delete = baseMapper.delete(eduCourseQueryWrapper);
        if (delete <= 0) {
            throw new GlobalCommonExceptionHandler(20001, "删除失败");
        }
    }

    @Override
    public List<EduCourse> getCourseByTeacherId(Long id) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public Page<EduCourse> getCourseAll(Long pageNum, Long pageSize, CourseInfoVO courseInfoVO) {

        Page<EduCourse> page = new Page<>(pageNum, pageSize);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String parentId = courseInfoVO.getSubjectParentId();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(parentId)) { //一级分类
            wrapper.eq("subject_parent_id",courseInfoVO.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseInfoVO.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseInfoVO.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseInfoVO.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseInfoVO.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseInfoVO.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        this.page(page, wrapper);


        return page;
    }

    @Override
    public CourseDataVO getCourseDataById(String id) {
        CourseDataVO baseCourseInfo = baseMapper.getBaseCourseInfo(id);
        return baseCourseInfo;
    }
}
