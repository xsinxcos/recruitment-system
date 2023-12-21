package com.achobeta.service.impl;

import com.achobeta.constants.SystemConstants;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.entity.LoginUser;
import com.achobeta.domain.entity.ScanStatusAuthRes;
import com.achobeta.domain.entity.User;
import com.achobeta.service.LoginService;
import com.achobeta.service.UserService;
import com.achobeta.utils.JwtUtil;
import com.achobeta.utils.RedisCache;
import com.achobeta.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final RedisCache redisCache;
    private final UserService userService;

    @Override
    public ResponseResult login(User user) {
        return null;
    }

    @Override
    public ResponseResult logout() {
        //获取userId
        Long userId = SecurityUtils.getUserId();
        //删除redis中的用户信息
        redisCache.deleteObject("adminLogin:" + userId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult scanStatus(String sceneStr) {
        ScanStatusAuthRes res = redisCache.getCacheObject("Qrcode" + sceneStr);
        if(res.isStatus()){
            redisCache.deleteObject("Qrcode" + sceneStr);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(res.getOpenId(),"");
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            //判断是否认证通过
            if(Objects.isNull(authenticate)){
                throw new RuntimeException("openID不存在");
            }
            //获取userid 生成token
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            String userId = String.valueOf(loginUser.getUser().getId());
            if(!userService.getById(userId).getType().equals(SystemConstants.ADMIN_TYPE)){
                throw new RuntimeException("无权限");
            }
            String jwt = JwtUtil.createJWT(userId);
            //把用户信息存入redis
            redisCache.setCacheObject("adminLogin:"+userId,loginUser);
            Map<String ,String> map = new TreeMap<>();
            map.put("token" ,jwt);
            return ResponseResult.okResult(map);
        }else {
            throw new RuntimeException("未登录");
        }
    }
}
