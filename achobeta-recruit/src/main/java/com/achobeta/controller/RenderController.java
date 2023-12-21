package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.service.RenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/render")
public class RenderController {
    private final RenderService renderService;
    @GetMapping
    @SystemLog(BusinessName = "获取用户端配图")
    public ResponseResult getPicture(Long id){
        return renderService.getPicture(id);
    }
}
