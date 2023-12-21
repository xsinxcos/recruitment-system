package com.achobeta.service;

import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.entity.User;

public interface RecruitLoginService {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult scanStatus(String sceneStr);
}
