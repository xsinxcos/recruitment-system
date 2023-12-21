package com.achobeta.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeListDto {
    private String period;
    private String name;
    private String grade;
    private String subject;
    private String station;
    private Integer status;
}
