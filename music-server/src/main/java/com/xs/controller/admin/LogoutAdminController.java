package com.xs.controller.admin;

import com.xs.common.Result;
import com.xs.service.LogoutService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class LogoutAdminController {

    @Resource
    private LogoutService logoutService;

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result logout() {
        return logoutService.logout();
    }
}
