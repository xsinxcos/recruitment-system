package com.achobeta.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowSendMessageVo {
    private Long id;
    private String title;
    //模板消息内容
    private String content;
}
