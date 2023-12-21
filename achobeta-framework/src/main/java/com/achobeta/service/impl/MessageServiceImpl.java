package com.achobeta.service.impl;

import com.achobeta.constants.SystemConstants;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.FeedbackDto;
import com.achobeta.domain.dto.MessageSendToUserDto;
import com.achobeta.domain.dto.UserMessageDto;
import com.achobeta.domain.entity.Message;
import com.achobeta.domain.entity.User;
import com.achobeta.domain.entity.UserMessage;
import com.achobeta.domain.vo.*;
import com.achobeta.mapper.MessageMapper;
import com.achobeta.service.MessageService;
import com.achobeta.service.UserMessageService;
import com.achobeta.service.UserService;
import com.achobeta.service.WechatMessageService;
import com.achobeta.utils.BeanCopyUtils;
import com.achobeta.utils.RedisCache;
import com.achobeta.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 对用户发送的模板消息(Message)表服务实现类
 *
 * @author makejava
 * @since 2023-11-08 08:58:09
 */
@Service("messageService")
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    private final UserMessageService userMessageService;
    private final WechatMessageService wechatMessageService;
    private final UserService userService;

    @Override
    public ResponseResult feedback(FeedbackDto feedbackDto) {
        Long userId = SecurityUtils.getUserId();
        String content = feedbackDto.getContent();
        Message message = new Message();
        message.setContent(content);
        message.setStatus(SystemConstants.MESSAGE_FEEDBACK);
        save(message);

        userMessageService.save(new UserMessage(userId ,message.getId() ,SystemConstants.MESSAGE_STATUS_UNREAD));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult messageList() {
        //获取userid
        Long userId = SecurityUtils.getUserId();
        //根据userid获取messageId及其状态
        QueryWrapper<UserMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("message_id ,status")
                .eq("user_id" ,userId);
        List<UserMessage> userMessages = userMessageService.list(queryWrapper);
        Map<Long, Integer> collect = userMessages.stream()
                .collect(Collectors.toMap(UserMessage::getMessageId, UserMessage::getStatus));

        List<MessageListVo> vos = new ArrayList<>();
        //根据获取message
        List<Long> ids = new ArrayList<>();
        Map<Long ,Integer> messageStatus = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : collect.entrySet()) {
            ids.add(entry.getKey());
        }
        //进行封装VO
        List<Message> messages = getBaseMapper().selectBatchIds(ids);
        messages.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        for (Message message : messages) {
            if(message.getStatus().equals(SystemConstants.MESSAGE_ANNOUNCE)){
                MessageListVo vo = BeanCopyUtils.copyBean(message, MessageListVo.class);
                vo.setReadStatus(collect.get(message.getId()));
                vos.add(vo);
            }
        }
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult getMessage(Long id) {
        LambdaQueryWrapper<UserMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserMessage::getUserId ,SecurityUtils.getUserId())
                .eq(Objects.nonNull(id) ,UserMessage::getMessageId ,id);
        if(wrapper.isEmptyOfNormal()){
            throw new RuntimeException("无权限");
        }
        Message byId = getById(id);
        GetMessageVo vo = BeanCopyUtils.copyBean(byId, GetMessageVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult sendMessageToUser(MessageSendToUserDto messageSendToUserDto) {
        //将消息保存
        Message message = BeanCopyUtils.copyBean(messageSendToUserDto.getMessage() ,Message.class);
        message.setStatus(SystemConstants.MESSAGE_ANNOUNCE);
        save(message);
        Long messageId = message.getId();
        //将消息与接收者关系保存
        List<UserMessage> userMessages = new ArrayList<>();
        for (Long aLong : messageSendToUserDto.getId()) {
            userMessages.add(new UserMessage(aLong ,messageId ,SystemConstants.MESSAGE_STATUS_UNREAD));
        }
        userMessageService.saveBatch(userMessages);
        //将消息进行发送
        for (Long userId : messageSendToUserDto.getId()) {
            wechatMessageService.sendTemplateMessage(userId ,messageSendToUserDto.getMessage().getTitle() ,
                    messageSendToUserDto.getMessage().getContent());
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateMessageStatusByAdmin(UserMessageDto userMessageDto) {
        Long userId = SecurityUtils.getUserId();
        User user = SecurityUtils.getLoginUser().getUser();
        LambdaQueryWrapper<UserMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserMessage::getMessageId, userMessageDto.getMessageId());
        UserMessage one = userMessageService.getOne(wrapper);
        one.setStatus(userMessageDto.getStatus());
        //删除原有的关系
        userMessageService.getBaseMapper().delete(wrapper);
        //重新保存
        userMessageService.save(one);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult showSendMessage() {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getStatus ,SystemConstants.MESSAGE_ANNOUNCE)
                .orderByDesc(Message::getCreateTime);
        List<Message> messages = list(wrapper);
        List<ShowSendMessageVo> vos = BeanCopyUtils.copyBeanList(messages , ShowSendMessageVo.class);
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult getUserListOfSendMessage(Long messageID) {
        LambdaQueryWrapper<UserMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(messageID) ,UserMessage::getMessageId ,messageID);
        List<UserMessage> userMessages = userMessageService.list(wrapper);
        //找到消息的接收者
        List<Long> ids = userMessages.stream()
                .map(UserMessage::getUserId)
                .collect(Collectors.toList());
        Map<Long, Integer> collect = userMessages.stream()
                .collect(Collectors.toMap(UserMessage::getUserId, UserMessage::getStatus));
        List<User> users = userService.getBaseMapper().selectBatchIds(ids);
        List<GetUserListOfSendMessageVo> vos = BeanCopyUtils.copyBeanList(users, GetUserListOfSendMessageVo.class);
        for (GetUserListOfSendMessageVo vo : vos) {
            vo.setStatus(collect.get(vo.getId()));
        }
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult listFeedBackMessage() {
        //找到消息类型为模板消息的消息
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getStatus ,SystemConstants.MESSAGE_FEEDBACK)
                .orderByDesc(Message::getCreateTime);
        List<Message> messages = list(wrapper);
        List<ListFeedBackMessageVo> vos = new ArrayList<>();
        for (Message message : messages) {
            //根据消息用户关系表找到相应用户
            UserMessage one = userMessageService.getOne(new LambdaQueryWrapper<UserMessage>()
                    .eq(UserMessage::getMessageId, message.getId()));
            //找到消息发送的用户
            User byId = userService.getById(one.getUserId());
            ListFeedBackMessageVo vo = new ListFeedBackMessageVo(
                    message.getId(),
                    byId.getId(),
                    byId.getNickname(),
                    byId.getAvatar(),
                    message.getContent(),
                    one.getStatus()
            );
            vos.add(vo);
        }
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult updateMessageStatusByUser(UserMessageDto userMessageDto) {
        Long userId = SecurityUtils.getUserId();
        User user = SecurityUtils.getLoginUser().getUser();
        LambdaQueryWrapper<UserMessage> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(UserMessage::getUserId, userId)
                .eq(UserMessage::getMessageId, userMessageDto.getMessageId());
        UserMessage one = userMessageService.getOne(wrapper);
        one.setStatus(userMessageDto.getStatus());
        //删除原有的关系
        userMessageService.getBaseMapper().delete(wrapper);
        //重新保存
        userMessageService.save(one);
        return ResponseResult.okResult();
    }
}

