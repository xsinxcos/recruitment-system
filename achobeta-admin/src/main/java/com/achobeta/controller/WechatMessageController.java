package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.service.WechatMessageService;
import com.achobeta.utils.WechatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/wechat")
@RequiredArgsConstructor
public class WechatMessageController {
    private final WechatMessageService wechatMessageService;
    @GetMapping(produces = "text/plain; charset=utf-8")
    public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echoStr = request.getParameter("echostr");
        String token = "chaos";
        if (WechatUtil.checkAccess(signature, timestamp, nonce, echoStr, token)) {
            response.getWriter().write(echoStr);
        }
    }

    @PostMapping
    public void receiveAndResponseMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
        request.setCharacterEncoding("UTF-8");
        // 在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        response.setCharacterEncoding("UTF-8");
        //如果有消息回复会返回消息的XML
        String message = wechatMessageService.receiveAndResponseMessage(request);
        responseMessage(message, response);
    }

    private void responseMessage(String message, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            if (message != null) {
                out.write(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
