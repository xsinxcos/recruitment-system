package com.achobeta.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TemplateMessageRep {
    private String touser;
    private String template_id;
    private Map<String ,String> data;

}
