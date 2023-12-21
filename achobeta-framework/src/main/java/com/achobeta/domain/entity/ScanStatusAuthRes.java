package com.achobeta.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScanStatusAuthRes extends QrCodeRes{
    private boolean status;
    private String openId;
}
