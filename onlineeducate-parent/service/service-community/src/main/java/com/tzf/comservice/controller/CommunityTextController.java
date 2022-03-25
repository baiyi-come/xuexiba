package com.tzf.comservice.controller;


import com.google.gson.internal.$Gson$Preconditions;
import com.tzf.commonutil.Resouce;
import com.tzf.comservice.entity.CommunityText;
import com.tzf.comservice.entity.vo.CommunityItem;
import com.tzf.comservice.entity.vo.CommunityTextVO;
import com.tzf.comservice.entity.vo.CommunityTitle;
import com.tzf.comservice.service.CommunityTextService;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/comservice/communitytext")
@CrossOrigin
public class CommunityTextController {

    @Autowired
    private CommunityTextService textService;


    /**
     * 保存发布内容
     * @param communityTextVO
     * @return
     */
    @DisableAuth
    @PostMapping("saveCommunityText")
    public Resouce saveCommunityText(
            @RequestBody CommunityTextVO communityTextVO,
            HttpServletRequest request
    ) {
        try {
            textService.saveCommunityText(communityTextVO,request);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error().message("保存失败,文件内容不符合规范，或文件太大");
        }
    }

    /**
     *
     * 获取某个社区里的详细内容
     * @param communityId
     * @return
     */
    @DisableAuth
    @PostMapping("getCommunityTextByCommunityId/{communityId}")
    public Resouce getCommunityTextByCommunityId(@PathVariable String communityId) {


        List<CommunityItem> list = textService.getCommunityTextByCommunityId(communityId);

        return Resouce.ok().data("list",list);
    }

    /**
     * 删除某篇文章
     * @param id
     * @return
     */
    @DisableAuth
    @PostMapping("removeCommunityText/{id}")
    public Resouce removeCommunityText(@PathVariable String id) {

        try {
            textService.removeCommuityById(id);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }

    }


    /**
     * 查询某一个文章信息
     * @param id
     * @return
     */
    @DisableAuth
    @PostMapping("getCommunityTextOne/{id}")
    public Resouce getCommunityTextOne(@PathVariable String id) {

        CommunityItem communityItem = textService.getCommunityTextOne(id);
        return Resouce.ok().data("communityItem", communityItem);
    }

    /**
     * 社区推荐的两个文章
     * 废弃功能
     * @return
     */@DisableAuth
    @PostMapping("getCommunityLimitTwo")
    public Resouce getCommunityLimitTwo(){
        List<CommunityTitle> list = textService.getCommunityLimitTwo();
        return Resouce.ok().data("list",list);
    }
}

