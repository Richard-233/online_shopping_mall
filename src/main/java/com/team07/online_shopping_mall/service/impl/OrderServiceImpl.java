package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.model.domain.Order;
import com.team07.online_shopping_mall.mapper.OrderMapper;
import com.team07.online_shopping_mall.model.dto.OrderItemDTO;
import com.team07.online_shopping_mall.model.vo.OrderItemVO;
import com.team07.online_shopping_mall.model.vo.OrderVO;
import com.team07.online_shopping_mall.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    OrderMapper orderMapper;

    @Override
    public OrderVO searchAllByUserId(Long userId) {
        List<OrderItemDTO> orderItemDTOList = orderMapper.searchAllByUserId(userId);
        return encapsulateData(orderItemDTOList);
    }

    @Override
    public OrderVO searchUserOrderByStatus(Long userId, Integer status) {
        List<OrderItemDTO> orderItemDTOList = orderMapper.searchUserOrderByStatus(userId, status);
        return encapsulateData(orderItemDTOList);
    }

    // 根据DTOList生成Map再封装为VOList
    private OrderVO encapsulateData(List<OrderItemDTO> orderItemDTOList){
        Map<Long, List<OrderItemDTO>> orderItemDTOMap = new HashMap<>();
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        for(OrderItemDTO orderItemDTO : orderItemDTOList){
            Long orderId = orderItemDTO.getOrderId();
            List<OrderItemDTO> orderItemList;
            if(orderItemDTOMap.containsKey(orderId)){
                orderItemList = orderItemDTOMap.get(orderId);
            }
            else{
                orderItemList = new ArrayList<>();
            }
            orderItemList.add(orderItemDTO);
            orderItemDTOMap.put(orderId,orderItemList);
        }
        for(Long key : orderItemDTOMap.keySet()){
            List<OrderItemDTO> orderItemList = orderItemDTOMap.get(key);
            OrderItemVO orderItemVO = new OrderItemVO();
            orderItemVO.setOrderId(key);
            orderItemVO.setOrderItemList(orderItemList);
            orderItemVOList.add(orderItemVO);
        }
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderList(orderItemVOList);
        return orderVO;
    }
}
