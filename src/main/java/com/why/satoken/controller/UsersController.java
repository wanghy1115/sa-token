package com.why.satoken.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.why.satoken.aspect.service.LogMethodCall;
import com.why.satoken.entity.bo.UserMessage;
import com.why.satoken.entity.po.Users;
import com.why.satoken.entity.base.Result;
import com.why.satoken.service.impl.UsersServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author muyu
 * @since 2025-01-23
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Resource
    private UsersServiceImpl usersService;


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/auth/doLogin")
    public String doLogin(@RequestParam String username, @RequestParam String password) {
        UserMessage userMessage = usersService.judgeLogin(username, password);
        if (userMessage != null) {
            StpUtil.login(userMessage.getUserId());
            //StpUtil.getSession()默认是根据用户的id获取的seesion，未登录的话，无法获取session
            StpUtil.getSession().set("userMessage", userMessage);
            return "登录成功";
        }
        return "登录失败";
    }


    /**
     * 查询登录状态
     * @return
     */
    @RequestMapping("/auth/isLogin")
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
         * StpUtil.getLoginIdAsString(): 10001
         */
        System.out.println(String.format("StpUtil.getTokenValue(): %s" , StpUtil.getTokenValue()));
        System.out.println(String.format("StpUtil.getTokenName(): %s" , StpUtil.getTokenName()));
        System.out.println(String.format("StpUtil.getTokenInfo(): %s" , StpUtil.getTokenInfo()));
        System.out.println(String.format("StpUtil.getLoginIdAsString(): %s" , StpUtil.getLoginIdAsString()));
        return StpUtil.getLoginIdAsString();
    }



    @LogMethodCall(securityLevel = "II")
    @PostMapping("/updateUser")
    public Result<Boolean> updateUser(@RequestBody Users user) {

        return Result.createSuccess(usersService.updateById(user));
    }

    @LogMethodCall()
    @SaCheckLogin
    @GetMapping("/pageList")
    public Result<List<Users>> pageList() {
        return Result.createSuccess(usersService.list());
    }

}
