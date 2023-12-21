package com.achobeta.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TemplateMessageRes {
    private Integer errcode;
    private String errmsg;
    private Long msgid;
}
