package com.achobeta.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户简历关系表(UserResume)表实体类
 *
 * @author makejava
 * @since 2023-11-04 23:06:31
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ab_user_resume")
public class UserResume  {
    //用户主键ID@TableId
    private Long userId;
    //简历主键ID@TableId
    private Long resumeId;




}

