package com.tzf.comservice.controller;


import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.tzf.commonutil.Resouce;
import com.tzf.comservice.entity.CComment;
import com.tzf.comservice.entity.vo.CommunityTextComment;
import com.tzf.comservice.service.CCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baiyi
 * @since 2021-10-22
 */
@RestController
@RequestMapping("/comservice/comment")
@CrossOrigin
public class CCommentController {
    @Autowired
    private CCommentService cCommentService;

    /**
     * 保存文章评论
     *
     * @param cComment
     * @return
     */
    @PostMapping("saveCommunityTextComment")
    public Resouce saveCommunityTextComment(@RequestBody CComment cComment, HttpServletRequest request) {


        cCommentService.saveCommunityTextComment(cComment,request);
        return Resouce.ok();
    }

    /**
     * 根据文章id查询出该文章的评论
     * @param id 文章id
     * @return
     */
    @PostMapping("getCommunityCommentByTextId/{id}")
    public Resouce getCommunityCommentByTextId(@PathVariable String id) {

        List<CommunityTextComment> list = cCommentService.getCommunityCommentByTextId(id);
        return Resouce.ok().data("list", list);
    }

    /**
     * 删除二级评论
      */
    @GetMapping("removeCommunityTextTwoComment/{id}")
    public Resouce removeCommunityTextTwoComment(@PathVariable String id) {
        try {
            cCommentService.removeById(id);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error().message("删除失败");
        }
    }

    /**
     * 删除一级评论，连带删除其下的二级评论
     * @param id
     * @return
     */
    @GetMapping("removeCommunityTextOneComment/{id}")
    public Resouce removeCommunityTextOneComment(@PathVariable String id) {

        try {
            cCommentService.removeCommunityTextOneComment(id);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error().message("删除失败");
        }

    }
}

