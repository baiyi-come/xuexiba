package com.tzf.comservice.service;

import com.tzf.comservice.entity.CommunityText;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzf.comservice.entity.vo.CommunityItem;
import com.tzf.comservice.entity.vo.CommunityTextVO;
import com.tzf.comservice.entity.vo.CommunityTitle;

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
public interface CommunityTextService extends IService<CommunityText> {

    void saveCommunityText(CommunityTextVO communityTextVO, HttpServletRequest request);

    List<CommunityItem> getCommunityTextByCommunityId(String communityId);

    CommunityItem getCommunityTextOne(String id);

    List<CommunityTitle> getCommunityLimitTwo();

    void removeCommuityById(String id);

    List<CommunityText> getCommunityText(String id);
}
