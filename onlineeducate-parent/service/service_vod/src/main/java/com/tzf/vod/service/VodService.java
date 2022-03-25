package com.tzf.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    String saveVideo(MultipartFile file);

    void removeVideo(String id);

    void removeVideoBatch(List<String> videoIdList);
}
