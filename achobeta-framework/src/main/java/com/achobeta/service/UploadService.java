package com.achobeta.service;
import com.achobeta.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult uploadFile(MultipartFile file);
}
