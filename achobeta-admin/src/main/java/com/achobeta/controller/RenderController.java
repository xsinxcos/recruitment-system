package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.PictureUpdateDto;
import com.achobeta.service.RenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RenderController {
    private final RenderService renderService;
    @PutMapping("/picture")
    @SystemLog(BusinessName = "用户端配置图片")
    public ResponseResult updatePicture(@RequestBody PictureUpdateDto pictureUpdateDto){
        return renderService.updatePicture(pictureUpdateDto);
    }
}
