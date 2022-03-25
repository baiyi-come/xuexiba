package com.tzf.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzf.commonutil.JwtUtils;
import com.tzf.eduservice.clients.UcenterClients;
import com.tzf.eduservice.entity.EduComment;
import com.tzf.eduservice.mapper.EduCommentMapper;
import com.tzf.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzf.eduservice.service.EduCourseService;
import com.tzf.eduservice.service.EduTeacherService;
import com.tzf.larservice.entity.UcenterMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private UcenterClients ucenterClients;

    /**
     * 发表评论
     * @param request
     * @param teacherId
     * @param courseId
     * @param comment
     */
    @Override
    public void saveComment(HttpServletRequest request,String teacherId, String courseId, String comment) {
        // new 一个EduComment对象用于组装
        EduComment eduComment = new EduComment();

        // 把讲师id存入对象
        eduComment.setTeacherId(teacherId);

        // 把课程id存入对象
        eduComment.setCourseId(courseId);

        // 获取当前用户
        String id = JwtUtils.getMemberIdByJwtToken(request);

        // 根据当前用户id查询用户信息
        UcenterMember ucenterMember = ucenterClients.getUcenterMember(id);
        // 根据查询结果整合到eduComment中
        eduComment.setAvatar(ucenterMember.getAvatar());
        eduComment.setNickname(ucenterMember.getNickname());
        eduComment.setMemberId(id);
        // 存入评论
        String commentUtf8 = toUTF8(comment);
        eduComment.setContent(comment);
        // 默认不删除
        eduComment.setIsDeleted(false);
        // 加入数据库
        baseMapper.insert(eduComment);
    }

    @Override
    public Page<EduComment> getComment(Long pageNum, Long pageSize,String courseId) {
        Page<EduComment> page = new Page<>(pageNum,pageSize);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        // 降序排列，最新评论优先显示
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("course_id", courseId);
        // 每页显示多少条评论
        //wrapper.last("limit 10");
        this.page(page, wrapper);
        return page;
    }

    @Override
    public void removeComment(String id) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        baseMapper.delete(wrapper);
    }


    /**
     * 无用代码
     * @param str
     * @return
     */
    public static String toUTF8(String str) {
        try {
            if (str.equals(new String(str.getBytes("GB2312"), "GB2312"))) {
                str = new String(str.getBytes("GB2312"), "utf-8");
                return str;
            }
        } catch (Exception exception) {
        }
        try {
            if (str.equals(new String(str.getBytes("ISO-8859-1"), "ISO-8859-1"))) {
                str = new String(str.getBytes("ISO-8859-1"), "utf-8");
                return str;
            }
        } catch (Exception exception1) {
        }
        try {
            if (str.equals(new String(str.getBytes("GBK"), "GBK"))) {
                str = new String(str.getBytes("GBK"), "utf-8");
                return str;
            }
        } catch (Exception exception3) {
        }
        return str;
    }
}
