package com.team07.online_shopping_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team07.online_shopping_mall.model.domain.Order;
import com.team07.online_shopping_mall.mapper.OrderMapper;
import com.team07.online_shopping_mall.model.domain.Shop;
import com.team07.online_shopping_mall.model.dto.OrderItemDTO;
import com.team07.online_shopping_mall.model.vo.OrderItemVO;
import com.team07.online_shopping_mall.model.vo.OrderVO;
import com.team07.online_shopping_mall.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team07.online_shopping_mall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    ShopService shopService;

    @Override
    public OrderVO searchAllOrder() {
        List<OrderItemDTO> orderItemDTOList = orderMapper.searchAllOrder();
        return encapsulateData(orderItemDTOList);
    }

    @Override
    public OrderVO searchOrderByStatus(Integer status) {
        List<OrderItemDTO> orderItemDTOList = orderMapper.searchOrderByStatus(status);
        if(status == 5){
            List<OrderItemDTO> orderItemDTOList6 = orderMapper.searchOrderByStatus(6);
            orderItemDTOList.addAll(orderItemDTOList6);
        }
        return encapsulateData(orderItemDTOList);
    }

    @Override
    public OrderVO searchAllByUserId(Long userId) {
        List<OrderItemDTO> orderItemDTOList = orderMapper.searchAllByUserId(userId);
        return encapsulateData(orderItemDTOList);
    }

    @Override
    public OrderVO searchUserOrderByStatus(Long userId, Integer status) {
        List<OrderItemDTO> orderItemDTOList = orderMapper.searchUserOrderByStatus(userId, status);
        if(status == 5){
            List<OrderItemDTO> orderItemDTOList6 = orderMapper.searchUserOrderByStatus(userId, 6);
            orderItemDTOList.addAll(orderItemDTOList6);
        }
        return encapsulateData(orderItemDTOList);
    }

    @Override
    public OrderVO searchShopAllOrder(Long userId) {
        QueryWrapper<Shop> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Shop::getUserId,userId).eq(Shop::getOffline,0);
        Shop shop = shopService.getOne(wrapper);
        List<OrderItemDTO> orderItemDTOList = orderMapper.searchShopAllOrder(shop.getId());
        return encapsulateData(orderItemDTOList);
    }

    @Override
    public OrderVO searchShopOrderByStatus(Long userId, Integer status) {
        QueryWrapper<Shop> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Shop::getUserId,userId).eq(Shop::getOffline,0);
        Shop shop = shopService.getOne(wrapper);
        List<OrderItemDTO> orderItemDTOList = orderMapper.searchShopOrderByStatus(shop.getId(), status);
        if(status == 5){
            List<OrderItemDTO> orderItemDTOList6 = orderMapper.searchShopOrderByStatus(shop.getId(), 6);
            orderItemDTOList.addAll(orderItemDTOList6);
        }
        return encapsulateData(orderItemDTOList);
    }

    @Override
    public boolean buyerChangeOrderStatus(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            return false;
        }
        Integer status = order.getOrderStatus();
        if(status == 2){
            order.setOrderStatus(3);
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 3;
        }
        else if(status == 3){
            order.setOrderStatus(4).setEndTime(LocalDateTime.now());
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 4;
        }
        return false;
    }

    @Override
    public boolean sellerChangeOrderStatus(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            return false;
        }
        Integer status = order.getOrderStatus();
        if(status == 1){
            order.setOrderStatus(2).setDeliveryTime(LocalDateTime.now());
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 2;
        }
        return false;
    }

    @Override
    public boolean buyerRefund(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            return false;
        }
        Integer status = order.getOrderStatus();
        if(status == 1){
            order.setOrderStatus(7).setEndTime(LocalDateTime.now());
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 7;
        }
        else if (status == 2){
            order.setOrderStatus(5);
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 5;
        }
        else if (status == 3){
            order.setOrderStatus(6);
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 6;
        }
        return false;
    }

    @Override
    public boolean sellerAgreeRefund(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            return false;
        }
        Integer status = order.getOrderStatus();
        if(status == 5 || status == 6){
            order.setOrderStatus(7).setEndTime(LocalDateTime.now());
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 7;
        }
        return false;
    }

    @Override
    public boolean sellerRefuseRefund(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            return false;
        }
        Integer status = order.getOrderStatus();
        if(status == 5){
            order.setOrderStatus(2);
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 2;
        }
        else if (status == 6){
            order.setOrderStatus(3);
            orderMapper.updateById(order);
            return orderMapper.selectById(orderId).getOrderStatus() == 3;
        }
        return false;
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

    @Override
    public Integer scoreForOrder(Order order) {
        return orderMapper.updateShopScore(order.getShopId(),order.getScore(),order.getId());
    }
}
