package com.achobeta.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageListVo {
    private Long id;

    private String title;
    //已读为1，未读为0
    private Integer readStatus;



}
