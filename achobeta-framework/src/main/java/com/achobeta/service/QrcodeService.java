package com.achobeta.service;

import com.achobeta.domain.ResponseResult;
import me.chanjar.weixin.common.error.WxErrorException;

public interface QrcodeService {
    ResponseResult qrcodeCreate(Integer fromWhere) throws WxErrorException;

    public void getAuth(String sceneStr , String openId);
}
