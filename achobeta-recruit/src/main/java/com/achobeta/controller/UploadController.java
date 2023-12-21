package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    UploadService uploadService;
    @PostMapping("/upload")
//    @SystemLog(BusinessName = "文件上传")
    public ResponseResult upload(MultipartFile file){
        return uploadService.uploadFile(file);
    }
}
