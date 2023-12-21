package com.achobeta.service.impl;

import com.achobeta.domain.entity.UserMessage;
import com.achobeta.mapper.UserMessageMapper;
import com.achobeta.service.UserMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户模板消息/反馈消息关系表(UserMessage)表服务实现类
 *
 * @author makejava
 * @since 2023-11-08 09:37:35
 */
@Service("userMessageService")
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {

}

