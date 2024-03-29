package com.team07.online_shopping_mall.service;

import com.team07.online_shopping_mall.model.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team07.online_shopping_mall.model.vo.OrderVO;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author team07
 * @since 2022-02-27
 */
public interface OrderService extends IService<Order> {

    OrderVO searchAllByUserId(Long userId);

    OrderVO searchUserOrderByStatus(Long userId, Integer status);

    OrderVO searchShopAllOrder(Long userId);

    OrderVO searchShopOrderByStatus(Long userId, Integer status);

    boolean buyerChangeOrderStatus(Long orderId);

    boolean sellerChangeOrderStatus(Long orderId);

    boolean buyerRefund(Long orderId);

    boolean sellerAgreeRefund(Long orderId);

    boolean sellerRefuseRefund(Long orderId);

    OrderVO searchAllOrder();

    OrderVO searchOrderByStatus(Integer status);

    Integer scoreForOrder(Order order);
}
