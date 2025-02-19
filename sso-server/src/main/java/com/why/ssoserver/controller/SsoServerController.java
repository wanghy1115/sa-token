package com.why.ssoserver.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sign.SaSignUtil;
import cn.dev33.satoken.sso.config.SaSsoServerConfig;
import cn.dev33.satoken.sso.processor.SaSsoServerProcessor;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.dtflys.forest.Forest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Sa-Token-SSO Server端 Controller
 */
/**
 * Sa-Token-SSO Server端 Controller
 */
@RestController
public class SsoServerController {

    /**
     * SSO-Server端：处理所有SSO相关请求 (下面的章节我们会详细列出开放的接口)
     */
    @RequestMapping("/sso/*")
    public Object ssoRequest() {
        return SaSsoServerProcessor.instance.dister();
    }


    // 示例：获取数据接口（用于在模式三下，为 client 端开放拉取数据的接口）
    @RequestMapping("/sso/getData")
    public SaResult getData(String apiType, String loginId) {
        System.out.println("---------------- 获取数据 ----------------");
        System.out.println("apiType=" + apiType);
        System.out.println("loginId=" + loginId);

        // 校验签名：只有拥有正确秘钥发起的请求才能通过校验
        SaSignUtil.checkRequest(SaHolder.getRequest());

        // 自定义返回结果（模拟）
        return SaResult.ok()
                .set("id", loginId)
                .set("name", "LinXiaoYu")
                .set("sex", "女")
                .set("age", 18);
    }

    /**
     * 配置SSO相关参数
     */
    @Autowired
    private void configSso(SaSsoServerConfig ssoServer) {
        // 配置：未登录时返回的View
        ssoServer.notLoginView = () -> {
            String msg = "当前会话在SSO-Server端尚未登录，请先访问"
                    + "<a href='/sso/doLogin?name=sa&pwd=123456' target='_blank'> doLogin登录 </a>"
                    + "进行登录之后，刷新页面开始授权";
            return msg;
        };

        // 配置：登录处理函数
        ssoServer.doLoginHandle = (name, pwd) -> {
            // 此处仅做模拟登录，真实环境应该查询数据进行登录
            if("sa".equals(name) && "123456".equals(pwd)) {
                StpUtil.login(10001);
                return SaResult.ok("登录成功！").setData(StpUtil.getTokenValue());
            }
            return SaResult.error("登录失败！");
        };

        // 配置 Http 请求处理器 （在模式三的单点注销功能下用到，如不需要可以注释掉）
        ssoServer.sendHttp = url -> {
            try {
                System.out.println("------ 发起请求：" + url);
                String resStr = Forest.get(url).executeAsString();
                System.out.println("------ 请求结果：" + resStr);
                return resStr;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }


    // 获取指定用户的粉丝列表
    @RequestMapping("/sso/getFansList")
    public Object getFansList(Long loginId) {
        System.out.println("---------------- 获取 loginId=" + loginId + " 的粉丝列表 ----------------");

        // 校验签名：只有拥有正确秘钥发起的请求才能通过校验
        SaSignUtil.checkRequest(SaHolder.getRequest());

        // 查询数据 (此处仅做模拟)
        List<Integer> list = Arrays.asList(10041, 10042, 10043, 10044);

        // 返回
        return list;
    }


}
