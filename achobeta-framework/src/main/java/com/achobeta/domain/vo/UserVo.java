package com.achobeta.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private Long id;
    private Long openid;
    private String nickname;
    private String studentId;
    private String avatar;
    //0为普通用户，1为管理员
    private Integer type;
}
