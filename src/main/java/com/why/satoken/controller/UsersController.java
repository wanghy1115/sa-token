package com.why.satoken.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author muyu
 * @since 2025-01-23
 */
@RestController
@RequestMapping("/auth/users")
public class UsersController {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }


    /**
     * 查询登录状态
     * @return
     */
    @RequestMapping("/isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }


}
