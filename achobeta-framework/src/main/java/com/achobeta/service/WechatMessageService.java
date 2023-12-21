package com.achobeta.service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface WechatMessageService {
    public String  receiveAndResponseMessage(HttpServletRequest request) throws IOException;

    void sendTemplateMessage(Long userId, String title, String content);
}
