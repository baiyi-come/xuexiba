package com.tzf.eduservice.controller;


import com.tzf.commonutil.Resouce;
import com.tzf.eduservice.clients.VodClients;
import com.tzf.eduservice.entity.EduVideo;
import com.tzf.eduservice.service.EduVideoService;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Resource
    private EduVideoService eduVideoService;

    @Resource
    private VodClients vodClients;
    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @DisableAuth
    @PostMapping("saveEduVideo")
    public Resouce saveEduVideo(@RequestBody EduVideo eduVideo) {
        try {
            eduVideoService.save(eduVideo);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }

    /**
     * 删除小节
     * @param id
     * @return
     */
    @DisableAuth
    @DeleteMapping("{id}")
    public Resouce removeEduVideoById(@PathVariable String id) {
        try {
            // 获取Video基本信息
            EduVideo video = eduVideoService.getById(id);

            String sourceId = video.getVideoSourceId();
            System.out.println(sourceId);
            if (!StringUtils.isEmpty(sourceId)) {
                vodClients.removeVideo(sourceId);
            }
            eduVideoService.removeById(id);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }

    /**
     * 用id获取Video
     * @param id
     * @return
     */
    @DisableAuth
    @GetMapping("getVideoById/{id}")
    public Resouce getVideoById(@PathVariable String id) {
        try {
            EduVideo eduVideo = eduVideoService.getById(id);
            return Resouce.ok().data("eduVideo", eduVideo);
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }

    }

    /**
     * 更新音频
     * @param eduVideo
     * @return
     */
    @DisableAuth
    @PostMapping("updateVideo")
    public Resouce updateVideo(@RequestBody EduVideo eduVideo) {
        try {
            eduVideoService.updateById(eduVideo);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }
}

