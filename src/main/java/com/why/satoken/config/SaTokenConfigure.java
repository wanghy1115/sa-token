package com.why.satoken.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。此处是最简单的登录校验
//        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
//                .addPathPatterns("/**")
//                .excludePathPatterns("/users/auth/**");

        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定一条 match 规则
            SaRouter
                    .match("/**")    // 拦截的 path 列表，可以写多个 */
                    .notMatch("/users/auth/**")        // 排除掉的 path 列表，可以写多个
                    .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式

            SaRouter.match("/users/pageList", r -> StpUtil.checkLogin());
            // 根据路由划分模块，不同模块不同鉴权
            SaRouter.match("/books/addBook", r -> StpUtil.checkPermission("insert_book"));
            SaRouter.match("/books/deleteBook", r -> StpUtil.checkPermission("delete_book"));
            SaRouter.match("/books/updateBook", r -> StpUtil.checkPermission("update_book"));
            //SaRouter.match("/users/^(?!auth).*", r -> StpUtil.checkRole("*admin"));

            /**
             * stop和back
             * stop：如果匹配的话直接进去controller， 不会在进行后续鉴权匹配
             * back：如果匹配直接返回给前端对应值，不会进行后续匹配，不会进入controller
             */
            //SaRouter.match("/users/pageList", r -> StpUtil.checkLogin()).stop();
            SaRouter.match("/users/pageList", r -> StpUtil.checkRoleOr("admin", "super_admin"));
            // 执行back函数后将停止匹配，也不会进入Controller，而是直接将 back参数 作为返回值输出到前端
            SaRouter.match("/back").back("直接返回给前端信息");
        }).isAnnotation(false)//sa-token注入之后，默认会打开注解鉴权，这个设置成false，会关闭注解鉴权，只使用拦截器鉴权
        ).addPathPatterns("/**");
    }
}
