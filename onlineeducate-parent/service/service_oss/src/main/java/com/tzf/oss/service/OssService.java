package com.tzf.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadFile(MultipartFile file);
}
