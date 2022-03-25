package com.tzf.comservice.service;

import com.tzf.comservice.entity.CComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzf.comservice.entity.vo.CommunityTextComment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baiyi
 * @since 2021-10-22
 */
public interface CCommentService extends IService<CComment> {

    void saveCommunityTextComment(CComment cComment, HttpServletRequest request);

    List<CommunityTextComment> getCommunityCommentByTextId(String id);

    void removeCommunityTextOneComment(String id);
}
