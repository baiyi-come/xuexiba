package com.tzf.comservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzf.comservice.entity.Community;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface CommunityService extends IService<Community> {

    void saveCommunity(Community community, HttpServletRequest request);

    Page<Community> getCommunity(Long pageNum, Long pageSize);

    List<Community> getCommunityByName(String communityName);

    void removeCommunity(String id);
}
