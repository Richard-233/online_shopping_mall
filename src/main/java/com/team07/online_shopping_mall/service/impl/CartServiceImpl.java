package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.model.domain.Cart;
import com.team07.online_shopping_mall.mapper.CartMapper;
import com.team07.online_shopping_mall.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-27
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Override
    public boolean addCartProduct(Cart cart){
        Cart oldCart = cartMapper.selectById(cart.getId());
        if(!(oldCart.getQuantity() == cart.getQuantity() && oldCart.getProductId() == cart.getProductId())){
            return false;
        }
        cart.setQuantity(cart.getQuantity() + 1);
        return cartMapper.updateById(cart) == 1;
    }

    @Override
    public boolean subCartProduct(Cart cart){
        Cart oldCart = cartMapper.selectById(cart.getId());
        if(!(oldCart.getQuantity() == cart.getQuantity() && oldCart.getProductId() == cart.getProductId())){
            return false;
        }
        if(cart.getQuantity() - 1 == 0){
            return delCartProduct(cart);
        }
        else {
            cart.setQuantity(cart.getQuantity() - 1);
            return cartMapper.updateById(cart) == 1;
        }
    }

    @Override
    public boolean delCartProduct(Cart cart){
        return cartMapper.deleteById(cart.getId()) == 1;
    }
}


