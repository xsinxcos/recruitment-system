package com.achobeta.controller;

import com.achobeta.annotation.SystemLog;
import com.achobeta.domain.ResponseResult;
import com.achobeta.domain.dto.AdminAddDto;
import com.achobeta.domain.dto.AdminDeleteDto;
import com.achobeta.domain.dto.AdminListDto;
import com.achobeta.domain.dto.UserListDto;
import com.achobeta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/admin/list")
    @SystemLog(BusinessName = "管理员列表展示")
    public ResponseResult adminList(AdminListDto adminListDto){
        return userService.adminList(adminListDto);
    }

    @PostMapping("/admin")
    @SystemLog(BusinessName = "添加管理员")
    public ResponseResult addAdmin(@RequestBody AdminAddDto adminAddDto){
        return userService.addAdmin(adminAddDto);
    }

    @DeleteMapping("/admin")
    @SystemLog(BusinessName = "删除管理员")
    public ResponseResult deleteAdmin(@RequestBody AdminDeleteDto adminDeleteDto){
        return userService.deleteAdmin(adminDeleteDto);
    }

    @GetMapping("/user/list")
    @SystemLog(BusinessName = "用户列表展示")
    public ResponseResult userList(UserListDto userListDto){
        return userService.userList(userListDto);
    }
}
