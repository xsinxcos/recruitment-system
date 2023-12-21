package com.achobeta.service.impl;

import com.achobeta.domain.entity.UserResume;
import com.achobeta.mapper.UserResumeMapper;
import com.achobeta.service.UserResumeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户简历关系表(UserResume)表服务实现类
 *
 * @author makejava
 * @since 2023-11-04 23:06:31
 */
@Service("userResumeService")
public class UserResumeServiceImpl extends ServiceImpl<UserResumeMapper, UserResume> implements UserResumeService {

}

