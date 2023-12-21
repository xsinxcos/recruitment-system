package com.achobeta.service;

import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.*;
import com.achobeta.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-10-25 00:25:26
 */
public interface UserService extends IService<User> {

    void addUser(String fromUser);

    ResponseResult userInfo();

    ResponseResult updateUserInfo(UserInfoUpdateDto userInfoUpdateDto);

    boolean checkAdmin(String openId);

    ResponseResult adminList(AdminListDto adminListDto);

    ResponseResult addAdmin(AdminAddDto adminAddDto);

    ResponseResult deleteAdmin(AdminDeleteDto adminDeleteDto);

    ResponseResult userList(UserListDto userListDto);
}

