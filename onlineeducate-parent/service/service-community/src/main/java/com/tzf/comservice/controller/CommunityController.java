package com.tzf.comservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzf.commonutil.Resouce;
import com.tzf.comservice.entity.Community;
import com.tzf.comservice.service.CommunityService;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/comservice/community")
@CrossOrigin
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    /**
     * 保存社区
     * @param community
     * @param request
     * @return
     */
    @DisableAuth
    @PostMapping("saveCommunity")
    public Resouce saveCommunity(@RequestBody Community community, HttpServletRequest request) {

        try {
            communityService.saveCommunity(community, request);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error().message("创建失败，名称已被注册");
        }
    }

    /**
     * 显示社区
     * @return
     */
    @PostMapping("getCommunity/{pageNum}/{pageSize}")
    public Resouce getCommunity(
            @PathVariable("pageNum") Long pageNum,
            @PathVariable("pageSize") Long pageSize
    ){

        Page<Community> page = communityService.getCommunity(pageNum, pageSize);
        List<Community> records = page.getRecords();
        long total = page.getTotal();
        return Resouce.ok().data("records", records).data("total",total);
    }

    /**
     * 根据社区名称查找社区（模糊查询）
     * @param communityName
     * @return
     */
    @DisableAuth
    @PostMapping("getCommunityByName/{communityName}")
    public Resouce getCommunityByName(@PathVariable("communityName") String communityName) {
        List<Community> list = communityService.getCommunityByName(communityName);
        System.out.println(list);
        return Resouce.ok().data("list", list);
    }

    /**
     * 解散社区，删除社区及社区里面的一切文件
     * @param id 社区id
     * @return
     */
    @GetMapping("removeCommunity/{id}")
    public Resouce removeCommunity(@PathVariable String id){
        try {
            communityService.removeCommunity(id);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error().message("删除失败");
        }
    }
}

