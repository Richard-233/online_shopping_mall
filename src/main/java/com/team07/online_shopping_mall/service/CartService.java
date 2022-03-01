package com.team07.online_shopping_mall.service;

import com.team07.online_shopping_mall.model.domain.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team07.online_shopping_mall.model.domain.OrderItem;
import com.team07.online_shopping_mall.model.domain.Product;
import com.team07.online_shopping_mall.model.dto.CartInfoDTO;
import com.team07.online_shopping_mall.model.dto.OrderInfoDTO;
import com.team07.online_shopping_mall.model.vo.CartVO;

import java.util.List;
import java.util.Map;

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

    Map<Long, List<OrderItem>> classifyByShop(List<Cart> cartList);

    void createOrder(Map<Long, List<OrderItem>> orderItemMap, List<OrderInfoDTO> orderInfoDTOList);

    CartVO searchCart(Long userId);
}
