package com.why.satoken.service.impl;

import com.why.satoken.auth.entity.Books;
import com.why.satoken.auth.mapper.BooksMapper;
import com.why.satoken.auth.service.IBooksService;
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
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements IBooksService {

}
