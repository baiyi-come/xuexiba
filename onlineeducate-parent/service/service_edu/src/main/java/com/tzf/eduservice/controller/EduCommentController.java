package com.tzf.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzf.commonutil.Resouce;
import com.tzf.eduservice.entity.EduComment;
import com.tzf.eduservice.service.EduCommentService;
import com.tzf.servicebase.config.DisableAuth;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    /**
     * 保存评论
     *
     * @param teacherId
     * @param courseId
     * @param comment
     * @return
     */
    @DisableAuth
    @PostMapping("saveComment/{teacherId}/{courseId}/{comment}")
    public Resouce saveComment(
            HttpServletRequest request,
            @PathVariable String teacherId,
            @PathVariable String courseId,
            @PathVariable String comment
    ) {
        commentService.saveComment(request,teacherId, courseId, comment);
        return Resouce.ok();
    }

    /**
     * 分页显示评论
     * @param pageNum
     * @param pageSize
     * @return
     */
    @DisableAuth
    @PostMapping("getComment/{pageNum}/{pageSize}/{courseId}")
    public Resouce getComment(
            @PathVariable("pageNum") Long pageNum,
            @PathVariable("pageSize") Long pageSize,
            @PathVariable("courseId") String courseId){
        Page<EduComment> page = commentService.getComment(pageNum, pageSize,courseId);
        long total = page.getTotal();
        List<EduComment> records = page.getRecords();
        return Resouce.ok().data("total",total).data("records",records);
    }


    /**
     * 删除评价
     * @param id
     * @return
     */
    @DisableAuth
    @PostMapping("removeComment/{id}")
    public Resouce removeComment(@PathVariable String id) {
        try {
            commentService.removeComment(id);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalCommonExceptionHandler(20001, "删除失败");
        }
    }
}

