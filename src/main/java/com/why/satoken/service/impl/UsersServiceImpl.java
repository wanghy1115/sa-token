package com.why.satoken.service.impl;

import com.why.satoken.entity.Users;
import com.why.satoken.dao.UsersMapper;
import com.why.satoken.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
