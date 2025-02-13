package com.why.satoken.controller;

import ch.qos.logback.core.util.StringUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.why.satoken.aspect.service.LogMethodCall;
import com.why.satoken.constants.PassConstants;
import com.why.satoken.entity.bo.UserMessage;
import com.why.satoken.entity.po.Users;
import com.why.satoken.entity.base.Result;
import com.why.satoken.service.impl.UsersServiceImpl;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.N;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
    @Resource
    private UsersServiceImpl usersService;


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/auth/doLogin")
    public SaResult doLogin(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String deviceType) {
        UserMessage userMessage = usersService.judgeLogin(username, password);
        if (userMessage != null) {
            //登录当前会话
            StpUtil.login(userMessage.getUserId(), deviceType);
            log.info("用户{}，在设备{}登录", username, deviceType);
            //StpUtil.getSession()默认是根据用户的id获取的seesion，未登录的话，无法获取session
            StpUtil.getSession().set("userMessage", userMessage);
            return SaResult.data(StpUtil.getTokenInfo());
        }
        return SaResult.data("登录失败");
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
         return StpUtil.getLoginIdAsString();
    }



    @LogMethodCall(securityLevel = "II")
    @PostMapping("/updateUser")
    public Result<Boolean> updateUser(@RequestBody Users user) {

        return Result.createSuccess(usersService.updateById(user));
    }

    @LogMethodCall(securityLevel = "I")
    @SaCheckLogin
    @GetMapping("/pageList")
    public Result<List<Users>> pageList() {
        return Result.createSuccess(usersService.list());
    }

    /**
     * 删除用户，需要通过二次认证
     * @param id
     * @return
     */
    @LogMethodCall(securityLevel = "III")
    @GetMapping("/deleteUser")
    public Result<String> deleteUser(@RequestParam String id) {
        //判断是否开启了删除用户的二级认证
        if(!StpUtil.isSafe("deleteUser")) {
            return Result.createError("请先通过二级认证");
        }
        StpUtil.closeSafe("deleteUser");
        if (usersService.removeById(id)) {
            return Result.createSuccess("删除成功");
        }
        return Result.createError("删除失败");
    }

    /**
     * 验证二次认证
     * @param pass
     * @param type
     * @return
     */
    @LogMethodCall(securityLevel = "I")
    @GetMapping("/openSafe")
    public Result<String> openSafe(@RequestParam String pass, @RequestParam String type) {
        UserMessage userMessage = (UserMessage)StpUtil.getSession().get("userMessage");
        Users byId = usersService.getById(userMessage.getUserId());
        if (byId != null && byId.getPassword().equals(pass)) {
            //开启安全验证60s
            StpUtil.openSafe(type,60);
            return Result.createSuccess("二级认证成功");
        }
        return Result.createError("二级认证失败");
    }


    /**
     * 切换用户案例
     * @param userId
     * @return
     */
    @LogMethodCall(securityLevel = "IV")
    @GetMapping("/switchIdentity")
    public Result<String> switchIdentity(@RequestParam String userId) {
        //切换用户
        StpUtil.switchTo(userId);
        System.out.println("当前用户:"+ StpUtil.getLoginId());
        //退出
        StpUtil.endSwitch();
        return Result.createSuccess("切换结束");
    }

    /**
     * 获取加密后的密码
     * Encryption
     */
    @GetMapping("/auth/encryption")
    public Result<String> encryption(@RequestParam String pass) {
        String res;
        //加密使用aesEncrypt，解密使用aesDecrypt
        res = SaSecureUtil.aesEncrypt(PassConstants.SYSMMETRIC_KEY, pass);
        return Result.createSuccess(res);
    }
    /**
     * 获取摘要后的密码
     */
    @GetMapping("/auth/sha")
    public Result<String> sha256(@RequestParam String pass) {
        String res;
        //加密使用aesEncrypt，解密使用aesDecrypt
        res = SaSecureUtil.sha256(pass);
        return Result.createSuccess(res);
    }

    /**
     * 获取非对称加密后的密码
     * Encryption
     */
    @GetMapping("/auth/private")
    public Result<String> rsaPass(@RequestParam String pass) {
        String res;
        //加密使用aesEncrypt，解密使用aesDecrypt
        res = SaSecureUtil.rsaEncryptByPublic(PassConstants.PUBLIC_KEY, pass);
        return Result.createSuccess(res);
    }



}
