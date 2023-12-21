package com.achobeta.service.impl;

import com.achobeta.constants.SystemConstants;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.*;
import com.achobeta.domain.entity.User;
import com.achobeta.domain.vo.ListAdminVo;
import com.achobeta.domain.vo.UserInfoVo;
import com.achobeta.domain.vo.UserVo;
import com.achobeta.mapper.UserMapper;
import com.achobeta.service.UserService;
import com.achobeta.utils.BeanCopyUtils;
import com.achobeta.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-10-25 00:25:27
 */
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void addUser(String fromUser) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenid ,fromUser);
        List<User> list = list(wrapper);
        if(list.size() >= 1) return;
        User user = new User();
        user.setOpenid(fromUser);
        save(user);
    }

    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();
        User byId = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(byId, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(UserInfoUpdateDto userInfoUpdateDto) {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        user.setNickname(userInfoUpdateDto.getNickname());
        user.setAvatar(userInfoUpdateDto.getAvatar());
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public boolean checkAdmin(String openId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(openId) ,User::getOpenid ,openId);
        List<User> users = list(wrapper);
        //因为openID的唯一性所以直接默认数组的大小为1
        return users.get(0).getType().equals(SystemConstants.ADMIN_TYPE);
    }

    @Override
    public ResponseResult adminList(AdminListDto adminListDto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getType ,SystemConstants.ADMIN_TYPE)
                .like(Objects.nonNull(adminListDto.getId()) ,User::getId ,adminListDto.getId())
                .like(Objects.nonNull(adminListDto.getNickname()) ,User::getNickname ,adminListDto.getNickname())
                .like(Objects.nonNull(adminListDto.getStudentId()) ,User::getStudentId ,adminListDto.getStudentId());
        List<User> users = list(wrapper);
        List<ListAdminVo> vos = BeanCopyUtils.copyBeanList(users, ListAdminVo.class);
        return ResponseResult.okResult(vos);
    }

    @Override
    public ResponseResult addAdmin(AdminAddDto adminAddDto) {
        //todo 可实现不同用户不同角色
        List<User> users = getBaseMapper().selectBatchIds(Arrays.asList(adminAddDto.getIds()));
        for (User user : users) {
            user.setType(SystemConstants.ADMIN_TYPE);
        }
        updateBatchById(users);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteAdmin(AdminDeleteDto adminDeleteDto) {
        List<User> users = getBaseMapper().selectBatchIds(adminDeleteDto.getIds());
        for (User user : users) {
            user.setType(SystemConstants.USER_TYPE);
        }
        updateBatchById(users);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userList(UserListDto userListDto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(userListDto.getId()) ,User::getId ,userListDto.getId())
                .like(Objects.nonNull(userListDto.getNickname()) ,User::getNickname ,userListDto.getNickname())
                .like(Objects.nonNull(userListDto.getStudentId()) ,User::getStudentId ,userListDto.getStudentId());
        List<User> users = list(wrapper);
        List<UserVo> vos = BeanCopyUtils.copyBeanList(users, UserVo.class);
        return ResponseResult.okResult(vos);
    }

}

