package com.achobeta.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendToUserDto {
    private List<Long> id;
    private MessageDto message;
}
