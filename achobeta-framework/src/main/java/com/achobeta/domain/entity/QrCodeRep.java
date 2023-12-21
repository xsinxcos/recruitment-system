package com.achobeta.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeRep {
    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为60秒
     */
    private Integer expire_seconds;

    /**
     * 二维码类型
     * QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值
     * QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
     */
    private String action_name;

    /**
     * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     */
    private String scene_str;

}
