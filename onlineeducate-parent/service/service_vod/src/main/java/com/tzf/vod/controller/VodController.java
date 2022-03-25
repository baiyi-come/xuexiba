package com.tzf.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.tzf.commonutil.Resouce;
import com.tzf.servicebase.config.DisableAuth;
import com.tzf.vod.service.VodService;
import com.tzf.vod.utils.CrowdUtils;
import com.tzf.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/serviceVod")
@CrossOrigin
public class VodController {

    @Resource
    private VodService vodService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 保存视频
     *
     * @param file
     * @return
     */
    @DisableAuth
    @PostMapping("saveVideo")
    public Resouce saveVideo(MultipartFile file) {
        String videoId = vodService.saveVideo(file);
        return Resouce.ok().data("videoId", videoId);
    }

    /**
     * 删除视频
     *
     * @param id
     * @return
     */
    @DisableAuth
    @DeleteMapping("removeVideo/{id}")
    public Resouce removeVideo(@PathVariable String id) {
        try {
            vodService.removeVideo(id);
            return Resouce.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }

    /**
     * 批量删除视频
     * @param videoIdList
     * @return
     */
    @DisableAuth
    @DeleteMapping("removeVideoBatch")
    public Resouce removeVideoBatch(@RequestParam("videoIdList") List<String> videoIdList) {

        vodService.removeVideoBatch(videoIdList);

        return Resouce.ok();
    }

//    =================前端课程视频播放=============================


    /**
     * 根据视频唯一凭证id获取视频
     * @param id
     * @return
     */
    @DisableAuth
    @GetMapping("getPlayAuth/{id}")
    public Resouce playVideo(@PathVariable String id)  {
        try {
//            System.out.println("33333");
            DefaultAcsClient client = InitObject.initVodClient(CrowdUtils.KEY_ID, CrowdUtils.KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();

            // 计算视频每天的播放次数，存入redis中
            String videocount = redisTemplate.opsForValue().get("videocount");
//            System.out.println(videocount);
            if (videocount == null||videocount=="") {
                redisTemplate.opsForValue().set("videocount", "1");
            } else {
                redisTemplate.opsForValue().increment("videocount");
            }
            return Resouce.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            return Resouce.error();
        }
    }

}


