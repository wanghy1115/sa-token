package com.why.satoken.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * sa-token将获取用户的权限的功能对外开放成了接口
 * 需要继承StpInterface方法
 * 并且注入到spring中
 *
 * 实现两个方法getPermissionList和getRoleList
 * Object loginId, String loginType。
 * loginId就是登录时候写入的值：StpUtil.login(10001);
 * 第二个参数是账号体系标识，多账户认证式使用
 */
@Service
public class StpInterfaceImpl implements StpInterface {
    public static HashMap<String, List<String >> permissionMap = new HashMap<>();
    public static HashMap<String, List<String >> roleMap = new HashMap<>();

    public StpInterfaceImpl() {
        permissionMap.put("10001", List.of("select_book", "update_book", "insert_book"));
        //可以使用*作为通配符
        permissionMap.put("10002", List.of("*_book"));
        roleMap.put("10001", List.of("admin", "super_admin"));
    }
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return permissionMap.get(String.valueOf(loginId));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleMap.get(String.valueOf(loginId));
    }
}
