package com.why.satoken.listener;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.why.satoken.aspect.MethodCallLoggerAspect;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaTokenLogListener implements SaTokenListener {
    private static final Logger loger = LoggerFactory.getLogger(SaTokenLogListener.class);

    //登录时触发
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        String token = loginModel.token;
        Object loginIdByToken = StpUtil.getLoginIdByToken(tokenValue);
        loger.info("用户:{},登录成功; loginId:{}", loginIdByToken, loginId);
    }
    //登出时触发
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {

    }
    //被强制下线时触发
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {

    }
    //被顶下去时触发
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {

    }
    //被禁用时触发
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {

    }
    //解禁时触发
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {

    }
    //二次认证时触发
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {

    }
    //退出二次认证时触发
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {

    }
    //创建session时触发
    @Override
    public void doCreateSession(String id) {

    }
    //登出session时触发
    @Override
    public void doLogoutSession(String id) {

    }
    //token续期时触发
    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {

    }
}
