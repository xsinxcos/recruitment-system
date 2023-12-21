package com.achobeta.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextMessageRes extends BaseRes{
    private String Content;

    public TextMessageRes(String ToUserName, String FromUserName, Long CreateTime, String MsgType, String content) {
        super(ToUserName, FromUserName, CreateTime, MsgType);
        Content = content;
    }
}
