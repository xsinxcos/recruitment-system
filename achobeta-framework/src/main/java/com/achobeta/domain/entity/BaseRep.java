package com.achobeta.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRep {
    // 发送方账号(用户方)
    private String fromUser;
    // 接受方账号（公众号）
    private String toUser;
    // 消息类型
    private String msgType;
    // 事件类型
    private String event;
}
