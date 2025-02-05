package com.why.satoken.service;

import com.why.satoken.entity.bo.UserMessage;
import com.why.satoken.entity.po.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muyu
 * @since 2025-01-23
 */
public interface IUsersService extends IService<Users> {
    UserMessage judgeLogin(String username, String password);
}
