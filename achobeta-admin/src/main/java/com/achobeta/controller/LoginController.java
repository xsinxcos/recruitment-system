package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.constants.SystemConstants;
import com.achobeta.domain.ResponseResult;
import com.achobeta.service.LoginService;
import com.achobeta.service.QrcodeService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    @GetMapping("/scanStatus")
    @SystemLog(BusinessName = "轮询查询登录状态")
    public ResponseResult scanStatus(String sceneStr){
        return loginService.scanStatus(sceneStr);
    }
    private final QrcodeService qrcodeService;
    @GetMapping("/scanLogin")
    @SystemLog(BusinessName = "获取登录二维码")
    public ResponseResult getQrcode() throws WxErrorException {
        return qrcodeService.qrcodeCreate(SystemConstants.REQUEST_FROM_ADMIN);
    }

    @PostMapping("/logout")
    @SystemLog(BusinessName = "注销登录")
    public ResponseResult logout(){
        return loginService.logout();
    }



}
