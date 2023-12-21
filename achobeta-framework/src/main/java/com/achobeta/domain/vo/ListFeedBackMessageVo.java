package com.achobeta.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListFeedBackMessageVo {
    private Long id;
    private Long userId;
    private String nickname;
    private String avatar;
    private String content;
    private Integer status;
}
