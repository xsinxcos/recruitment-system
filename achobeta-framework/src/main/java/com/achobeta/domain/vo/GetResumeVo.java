package com.achobeta.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetResumeVo {
    private String name;
    //学号
    private String studentId;
    //性别（0为男，1为女）
    private Integer sex;
    //年级
    private Integer grade;
    //专业
    private String subject;
    //班号
    private String classNumber;
    //意向岗位
    private String station;
    //手机号码
    private String phoneNumber;
    //微信号
    private String wechatId;
    //自我介绍
    private String introduction;
    //加入原因
    private String reason;
    //个人经历 (项目经历、 职业规划等)
    private String experience;
    //获奖情况
    private String honors;
    //头像
    private String avatar;
    //附件数组
    private String annex;
    //备注
    private String remark;
    //- 草稿 0- 待筛选  1- 筛选不通过 2 - 待安排初试 3 - 待初试  4- 初试不通过 5- 初试通过 6- 待复试 7- 待安排复试 8- 复试通过 9- 待终试 10- 待安排终试 11- 终试通过 12- 待处理 13- 挂起 -14
    private Integer status;
}
