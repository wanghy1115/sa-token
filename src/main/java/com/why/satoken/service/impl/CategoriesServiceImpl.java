package com.why.satoken.service.impl;

import com.why.satoken.auth.entity.Categories;
import com.why.satoken.auth.mapper.CategoriesMapper;
import com.why.satoken.auth.service.ICategoriesService;
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
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories> implements ICategoriesService {

}
