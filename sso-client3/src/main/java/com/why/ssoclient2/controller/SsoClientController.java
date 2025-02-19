package com.why.ssoclient2.controller;


import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sign.SaSignUtil;
import cn.dev33.satoken.sso.SaSsoManager;
import cn.dev33.satoken.sso.processor.SaSsoClientProcessor;
import cn.dev33.satoken.sso.template.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Sa-Token-SSO Client端 Controller
 */
@RestController
public class SsoClientController {

    // 首页
    // SSO-Client端：首页
    @RequestMapping("/")
    public String index() {
        String str = "<h2>Sa-Token SSO-Client 应用端</h2>" +
                "<p>当前会话是否登录：" + StpUtil.isLogin() + "</p>" +
                "<p><a href=\"javascript:location.href='/sso/login?back=' + encodeURIComponent(location.href);\">登录</a>" +
                " <a href='/sso/logout?back=self'>注销</a></p>";
        return str;
    }


    /*
     * SSO-Client端：处理所有SSO相关请求
     *         http://{host}:{port}/sso/login          -- Client端登录地址，接受参数：back=登录后的跳转地址
     *         http://{host}:{port}/sso/logout         -- Client端单点注销地址（isSlo=true时打开），接受参数：back=注销后的跳转地址
     *         http://{host}:{port}/sso/logoutCall     -- Client端单点注销回调地址（isSlo=true时打开），此接口为框架回调，开发者无需关心
     */
    @RequestMapping("/sso/*")
    public Object ssoRequest() {
        return SaSsoClientProcessor.instance.dister();
    }


    // 查询我的账号信息
    @RequestMapping("/sso/myInfo")
    public Object myInfo() {
        // 组织请求参数
        Map<String, Object> map = new HashMap<>();
        map.put("apiType", "userinfo");
        map.put("loginId", StpUtil.getLoginId());

        // 发起请求
        Object resData = SaSsoUtil.getData(map);
        System.out.println("sso-server 返回的信息：" + resData);
        return resData;
    }

    // 查询我的账号信息
    @RequestMapping("/sso/myFansList")
    public Object myFansList() {
        // 组织请求参数
        Map<String, Object> map = new HashMap<>();
        // map.put("apiType", "userinfo");   // 此时已经不需要 apiType 参数了
        map.put("loginId", StpUtil.getLoginId());

        // 发起请求 （传入自定义的 path 地址）
        Object resData = SaSsoUtil.getData("/sso/getFansList", map);
        System.out.println("sso-server 返回的信息：" + resData);
        return resData;
    }




}
