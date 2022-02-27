package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.model.domain.Order;
import com.team07.online_shopping_mall.mapper.OrderMapper;
import com.team07.online_shopping_mall.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
