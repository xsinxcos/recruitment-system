package com.achobeta.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户模板消息/反馈消息关系表(UserMessage)表实体类
 *
 * @author makejava
 * @since 2023-11-08 09:37:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageDto {
    //模板消息ID@TableId
    private Long messageId;
    //用户消息的状态
    //0代表未处理，1代表已处理
    private Integer status;



}

