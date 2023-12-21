package com.achobeta.domain.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 渲染页面的图片(Render)表实体类
 *
 * @author makejava
 * @since 2023-11-07 23:43:59
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ab_render")
public class Render  {
    @TableId
    private Long id;

    
    private String url;



}

