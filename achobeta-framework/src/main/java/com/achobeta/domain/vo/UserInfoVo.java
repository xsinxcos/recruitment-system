package com.achobeta.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoVo {
    private String nickname;
    private String studentId;
    private String avatar;
}
