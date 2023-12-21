package com.achobeta.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2023-11-14 00:11:10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User  {
    //主键id@TableId
    private Long id;

    //微信openid
    private String openid;
    //用户名
    private String nickname;
    //学号
    private String studentId;
    //头像
    private String avatar;
    //订阅标识，订阅为0，未订阅为1
    private Integer status;
    //0为普通用户，1为管理员
    private Integer type;
    //创建者
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0为正常，1为删除）
    private Integer delFlag;
    //备注
    private String remark;

}

