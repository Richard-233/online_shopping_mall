package com.team07.online_shopping_mall.web.controller;

import com.team07.online_shopping_mall.common.ApiRestReasponse;
import com.team07.online_shopping_mall.model.domain.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartControllerTest {

    @Autowired
    CartController cartController;

    @Test
    void addToCart() throws Exception {
        Cart cart = new Cart().setUserId(2L).setProductId(1L).setQuantity(1);
        ApiRestReasponse apiRestReasponse = cartController.addToCart(cart);
        System.out.println(apiRestReasponse);
    }
}
