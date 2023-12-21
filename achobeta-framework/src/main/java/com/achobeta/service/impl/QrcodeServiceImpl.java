package com.achobeta.service.impl;

import com.achobeta.constants.SystemConstants;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.entity.QrCodeRep;
import com.achobeta.domain.entity.QrCodeRes;
import com.achobeta.domain.entity.ScanStatusAuthRes;
import com.achobeta.domain.vo.QrcodeVo;
import com.achobeta.service.QrcodeService;
import com.achobeta.service.UserService;
import com.achobeta.utils.BeanCopyUtils;
import com.achobeta.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class QrcodeServiceImpl implements QrcodeService {
    private final RedisCache redisCache;
    private final WxMpService wxMpService;
    private final UserService userService;

    @Override
    public ResponseResult qrcodeCreate(Integer requestFormWhere) throws WxErrorException {
        QrCodeRep codeRep = new QrCodeRep();
        codeRep.setAction_name("QR_STR_SCENE");
        codeRep.setExpire_seconds(5 * 60);
        String loginScene = "";
        if(requestFormWhere.equals(SystemConstants.REQUEST_FROM_RECRUIT)) {
            // 设置场景值
            loginScene = new Date().getTime() + "ab";
        } else if (requestFormWhere.equals(SystemConstants.REQUEST_FROM_ADMIN)) {
            loginScene = new Date().getTime() + "admin";
        }
        codeRep.setScene_str(loginScene);
        WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(codeRep.getScene_str(), codeRep.getExpire_seconds());
        QrCodeRes res = new QrCodeRes();
        res.setSceneStr(codeRep.getScene_str());
        try {
            // 通过ticket获取二维码
            String ticketUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8");
            res.setTicketUrl(ticketUrl);
            res.setUrl(ticketUrl);
            res.setExpire_seconds(ticket.getExpireSeconds());
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 将状态写入redis
        QrcodeVo qrcodeVo = BeanCopyUtils.copyBean(res, QrcodeVo.class);
        ScanStatusAuthRes AuthRes = BeanCopyUtils.copyBean(res ,ScanStatusAuthRes.class);
        AuthRes.setStatus(false);
        redisCache.setCacheObject("Qrcode" + qrcodeVo.getSceneStr(), AuthRes, res.getExpire_seconds(), TimeUnit.SECONDS);
        return ResponseResult.okResult(qrcodeVo);
    }

    @Override
    public void getAuth(String sceneStr , String openId) {
        if(sceneStr.contains("qrscene_")){
            sceneStr = sceneStr.substring(8);
        }
        //区分该二维码是管理端还是用户端
        if(sceneStr.contains("admin") && !userService.checkAdmin(openId)) {
                throw new RuntimeException("该用户暂无权限");
        }
        ScanStatusAuthRes AuthRes = redisCache.getCacheObject("Qrcode" + sceneStr);
        // 如果扫码后，loginId已过期则要求用户重新刷新二维码，并且只要授权不成功都需要重新刷新二维码再次执行授权流程
        if (Objects.isNull(AuthRes)) {
            throw new RuntimeException("二维码过期请重新刷新");
        }
        AuthRes.setOpenId(openId);
        AuthRes.setStatus(true);
        redisCache.setCacheObject("Qrcode" + sceneStr ,AuthRes);
    }
}
