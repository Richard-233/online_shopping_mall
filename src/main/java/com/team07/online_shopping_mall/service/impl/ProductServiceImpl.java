package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.model.domain.Product;
import com.team07.online_shopping_mall.mapper.ProductMapper;
import com.team07.online_shopping_mall.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
