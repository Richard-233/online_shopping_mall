package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.model.domain.Cart;
import com.team07.online_shopping_mall.mapper.CartMapper;
import com.team07.online_shopping_mall.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

}
