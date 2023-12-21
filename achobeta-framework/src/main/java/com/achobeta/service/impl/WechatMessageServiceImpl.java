package com.achobeta.service.impl;

import com.achobeta.constants.SystemConstants;
import com.achobeta.constants.WechatMessageType;
import com.achobeta.domain.entity.BaseRep;
import com.achobeta.domain.entity.BaseRes;
import com.achobeta.domain.entity.TextMessageRes;
import com.achobeta.service.QrcodeService;
import com.achobeta.service.UserService;
import com.achobeta.service.WechatMessageService;
import com.achobeta.utils.BeanCopyUtils;
import com.achobeta.utils.WechatUtil;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service("MessageService")
@RequiredArgsConstructor
public class WechatMessageServiceImpl implements WechatMessageService {
    private final QrcodeService qrcodeService;
    private final UserService userService;
    private final WxMpService wxMpService;
    @Override
    public String receiveAndResponseMessage(HttpServletRequest request) throws IOException {
        // 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
        WxMpXmlMessage xmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        BaseRep baseRep = BeanCopyUtils.copyBean(xmlMessage, BaseRep.class);
        String event = baseRep.getEvent();
        String resMessage = null;
        switch (event){
            case WechatMessageType.EVENT_TYPE_SCAN:
                resMessage = qrcodeScan(baseRep ,xmlMessage);
                break;
            case WechatMessageType.EVENT_TYPE_SUBSCRIBE:
                subscribe(baseRep);
                if(Objects.nonNull(xmlMessage.getTicket())) resMessage = qrcodeScan(baseRep ,xmlMessage);
                break;
            default:break;
        }

        return resMessage;
    }

    @Override
    public void sendTemplateMessage(Long userId, String title, String content) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(SystemConstants.TEMPLATE_MESSAGE_ID_1);
        wxMpTemplateMessage.setToUser(userService.getById(userId).getOpenid());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first",userService.getById(userId).getNickname()),
                new WxMpTemplateData("keyword1",title),
                new WxMpTemplateData("keyword2",content)
        );
        wxMpTemplateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            throw new RuntimeException("【消息推送】发送失败");
        }
    }

    private String qrcodeScan(BaseRep baseRep ,WxMpXmlMessage xmlMessage){
        String openId = baseRep.getFromUser();
        // 进行后续的授权处理
        String eventKey = xmlMessage.getEventKey();
        String message = SystemConstants.LOGIN_SUCCESS;
        try {
            qrcodeService.getAuth(eventKey, openId);
        }catch (RuntimeException e){
            message = SystemConstants.LOGIN_FAIL;
            //发送文字消息
            return responseTextMessage(baseRep, message);
        }
        return responseTextMessage(baseRep, message);
    }

    private void subscribe(BaseRep baseRep){
        userService.addUser(baseRep.getFromUser());
    }

    private String responseTextMessage(BaseRep baseRep ,String content){
        BaseRes res = new TextMessageRes(baseRep.getFromUser() ,baseRep.getToUser() ,System.currentTimeMillis() /1000L,
                SystemConstants.MESSAGE_TYPE_TEXT ,content);
        return WechatUtil.obj2Xml(res);
    }
}
