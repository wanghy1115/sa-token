package com.why.satoken.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.why.satoken.constants.PassConstants;
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
        /**
         * plainTextVerification:明文校验
         * symmetryVerification:对称密钥
         * sha256Verification：哈希校验
         *
         */
        if (!rsaVerification(users.getPassword(), password)) {
            return res;
        }
        res = new UserMessage();
        BeanUtils.copyProperties(users, res, UserMessage.class);
        return res;
    }

    /**
     * 明文校验
     * @param password_right
     * @param password_judge
     * @return
     */
    private boolean plainTextVerification (String password_right, String password_judge) {
        return password_right.equals(password_judge);
    }

    /**
     * 对称密钥校验
     * @param password_right
     * @param password_judge
     * @return
     */
    private boolean symmetryVerification (String password_right, String password_judge) {
        return password_right.equals(SaSecureUtil.aesDecrypt(PassConstants.SYSMMETRIC_KEY, password_judge));
    }

    /**
     * 对称密钥校验
     * @param password_right
     * @param password_judge
     * @return
     */
    private boolean sha256Verification (String password_right, String password_judge) {
        return password_right.equals( SaSecureUtil.sha256(password_judge));
    }


    /**
     * 非对称
     * @param password_right
     * @param password_judge
     * @return
     */
    private boolean rsaVerification (String password_right, String password_judge) {
        return password_right.equals(SaSecureUtil.rsaDecryptByPrivate(PassConstants.PRIVATE_KEY, password_judge));
    }



}
