package com.achobeta.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAdminVo {
    private Long id;
    private String openid;
    private String nickname;
    private String studentId;
    private String avatar;
}
