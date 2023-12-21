package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.FeedbackDto;
import com.achobeta.domain.dto.UserMessageDto;
import com.achobeta.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    @PostMapping("/feedback")
    @SystemLog(BusinessName = "用户反馈消息发送")
    public ResponseResult feedback(@RequestBody FeedbackDto feedbackDto){
        return messageService.feedback(feedbackDto);
    }

    @GetMapping("/messageList")
    @SystemLog(BusinessName = "用户邮箱信息展示")
    public ResponseResult messageList(){
        return messageService.messageList();
    }

    @GetMapping("/message")
    @SystemLog(BusinessName = "信息详细展示")
    public ResponseResult getMessage(Long id){
        return messageService.getMessage(id);
    }

    @PutMapping("/message")
    @SystemLog(BusinessName = "更新消息状态")
    public ResponseResult updateMessageStatus(@RequestBody UserMessageDto userMessageDto){
        return messageService.updateMessageStatusByUser(userMessageDto);
    }
}
