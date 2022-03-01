package com.team07.online_shopping_mall.web.controller;

import com.team07.online_shopping_mall.common.ApiRestReasponse;
import com.team07.online_shopping_mall.mapper.CartMapper;
import com.team07.online_shopping_mall.model.domain.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class CartControllerTest {

    @Autowired
    CartController cartController;

    @Autowired
    CartMapper cartMapper;

    @Test
    void addToCart() throws Exception {
        Cart cart = new Cart().setUserId(2L).setProductId(1L).setQuantity(1);
        ApiRestReasponse apiRestReasponse = cartController.addToCart(cart);
        System.out.println(apiRestReasponse);
    }

    @Test
    void searchCart(){
        System.out.println(cartMapper.searchCart(1L));
    }
    
    @Test
    void mapTest(){
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1,2);
        map.put(3,4);
        System.out.println(map);
    }
}
