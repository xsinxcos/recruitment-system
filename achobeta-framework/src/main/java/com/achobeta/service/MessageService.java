package com.achobeta.service;

import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.FeedbackDto;
import com.achobeta.domain.dto.MessageSendToUserDto;
import com.achobeta.domain.dto.UserMessageDto;
import com.achobeta.domain.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 对用户发送的模板消息(Message)表服务接口
 *
 * @author makejava
 * @since 2023-11-07 23:48:16
 */
public interface MessageService extends IService<Message> {

    ResponseResult feedback(FeedbackDto feedbackDto);

    ResponseResult messageList();

    ResponseResult getMessage(Long id);

    ResponseResult sendMessageToUser(MessageSendToUserDto messageSendToUserDto);

    ResponseResult updateMessageStatusByAdmin(UserMessageDto userMessageDto);

    ResponseResult showSendMessage();

    ResponseResult getUserListOfSendMessage(Long messageID);

    ResponseResult listFeedBackMessage();

    ResponseResult updateMessageStatusByUser(UserMessageDto userMessageDto);
}

