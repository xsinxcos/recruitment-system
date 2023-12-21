package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {
    private final UploadService uploadService;
    @PostMapping()
    public ResponseResult upload(MultipartFile file){
        return uploadService.uploadFile(file);
    }

}
