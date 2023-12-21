package com.achobeta.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListResumeVo {
    private Long id;
    private String name;
    private String grade;
    private String subject;
    private Integer sex;
    private String phoneNumber;
    private String station;
    private Integer status;
}
