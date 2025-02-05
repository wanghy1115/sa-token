package com.why.satoken.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.why.satoken.entity.bo.UserMessage;
import com.why.satoken.entity.po.Users;
import com.why.satoken.dao.UsersMapper;
import com.why.satoken.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author muyu
 * @since 2025-01-23
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {


    @Override
    public UserMessage judgeLogin(String username, String password) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername, username);
        Users users = this.baseMapper.selectOne(wrapper);
        UserMessage res = null;
        if (users != null && users.getPassword().equals(password)) {
            res = new UserMessage();
            BeanUtils.copyProperties(users, res, UserMessage.class);
        }
        return res;
    }
}
