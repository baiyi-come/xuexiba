package com.tzf.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import com.tzf.vod.service.VodService;
import com.tzf.vod.utils.CrowdUtils;
import com.tzf.vod.utils.InitObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String saveVideo(MultipartFile file) {
        try {
            //accessKeyId, accessKeySecret
            //fileName：上传文件原始名称
            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(CrowdUtils.KEY_ID,CrowdUtils.KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeVideo(String id) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(CrowdUtils.KEY_ID, CrowdUtils.KEY_SECRET);
            DeleteVideoResponse response = new DeleteVideoResponse();
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(id);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GlobalCommonExceptionHandler(20001, "删除失败");
        }
    }

    @Override
    public void removeVideoBatch(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(CrowdUtils.KEY_ID, CrowdUtils.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 添加逗号
            String join = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(join);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GlobalCommonExceptionHandler(20001, "删除失败");
        }
    }
}
