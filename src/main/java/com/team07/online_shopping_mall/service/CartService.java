package com.team07.online_shopping_mall.service;

import com.team07.online_shopping_mall.model.domain.Cart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 购物车 服务类
 * </p>
 *
 * @author team07
 * @since 2022-02-27
 */
public interface CartService extends IService<Cart> {


    boolean addCartProduct(Cart cart);

    boolean subCartProduct(Cart cart);

    boolean delCartProduct(Cart cart);
}
