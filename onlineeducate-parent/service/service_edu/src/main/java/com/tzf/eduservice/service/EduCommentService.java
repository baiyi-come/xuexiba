package com.tzf.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzf.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;


import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 */
public interface EduCommentService extends IService<EduComment> {

    void saveComment(HttpServletRequest request,String teacherId, String courseId, String comment);

    Page<EduComment> getComment(Long pageNum, Long pageSize,String courseId);

    void removeComment(String id);
}
