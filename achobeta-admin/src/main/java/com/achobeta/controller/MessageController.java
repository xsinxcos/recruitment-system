package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.MessageSendToUserDto;
import com.achobeta.domain.dto.UserMessageDto;
import com.achobeta.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    @SystemLog(BusinessName = "发送消息给指定用户")
    public ResponseResult sendMessageToUser(@RequestBody MessageSendToUserDto messageSendToUserDto){
        return messageService.sendMessageToUser(messageSendToUserDto);
    }

    @GetMapping("/send")
    @SystemLog(BusinessName = "展示发送的消息")
    public ResponseResult showSendMessage(){
        return messageService.showSendMessage();
    }

    @GetMapping("/send/{id}")
    @SystemLog(BusinessName = "展示发送的消息详情")
    public ResponseResult getUserListOfSendMessage(@PathVariable Long id){
        return messageService.getUserListOfSendMessage(id);
    }

    @GetMapping("/receive")
    @SystemLog(BusinessName = "展示用户反馈消息")
    public ResponseResult listFeedBackMessage(){
        return messageService.listFeedBackMessage();
    }

    @PutMapping("/receive")
    @SystemLog(BusinessName = "展示用户反馈消息状态")
    public ResponseResult updateMessageStatus(@RequestBody UserMessageDto userMessageDto){
        return messageService.updateMessageStatusByAdmin(userMessageDto);
    }
}
