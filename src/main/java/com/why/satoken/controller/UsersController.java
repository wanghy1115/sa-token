package com.why.satoken.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String doLogin(@RequestParam String username, @RequestParam String password) {
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
        //StpUtil.isLogin()返回的是true和false，StpUtil.checkLogin()如果没有登录会抛出异常NotLoginException
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping("/doLogout")
    public String doLogout() {
        StpUtil.logout();
        return "退出成功";
    }

    /**
     * 获取当前会话的id
     * @return
     */
    @RequestMapping("/getLoginId")
    public String getLoginId() {
        /**
         * StpUtil.getTokenValue(): 56724a0b-6b5a-4b4a-ba56-8a5e28b80165
         * StpUtil.getTokenName(): satoken
         * StpUtil.getTokenInfo(): SaTokenInfo [tokenName=satoken, tokenValue=56724a0b-6b5a-4b4a-ba56-8a5e28b80165, isLogin=true, loginId=10001, loginType=login, tokenTimeout=2591994, sessionTimeout=2591994, tokenSessionTimeout=-2, tokenActiveTimeout=-1, loginDevice=default-device, tag=null]
         * StpUtil.getLoginIdAsString(): 123456
         */
        System.out.println(String.format("StpUtil.getTokenValue(): %s" , StpUtil.getTokenValue()));
        System.out.println(String.format("StpUtil.getTokenName(): %s" , StpUtil.getTokenName()));
        System.out.println(String.format("StpUtil.getTokenInfo(): %s" , StpUtil.getTokenInfo()));
        System.out.println(String.format("StpUtil.getLoginIdAsString(): %s" , StpUtil.getLoginIdAsString()));
        return StpUtil.getLoginIdAsString();
    }

}
