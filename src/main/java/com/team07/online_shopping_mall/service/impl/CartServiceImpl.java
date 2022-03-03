package com.team07.online_shopping_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team07.online_shopping_mall.mapper.OrderItemMapper;
import com.team07.online_shopping_mall.mapper.OrderMapper;
import com.team07.online_shopping_mall.mapper.ProductMapper;
import com.team07.online_shopping_mall.model.domain.Cart;
import com.team07.online_shopping_mall.mapper.CartMapper;
import com.team07.online_shopping_mall.model.domain.Order;
import com.team07.online_shopping_mall.model.domain.OrderItem;
import com.team07.online_shopping_mall.model.domain.Product;
import com.team07.online_shopping_mall.model.dto.CartInfoDTO;
import com.team07.online_shopping_mall.model.dto.OrderInfoDTO;
import com.team07.online_shopping_mall.model.vo.CartInfoVO;
import com.team07.online_shopping_mall.model.vo.CartVO;
import com.team07.online_shopping_mall.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public CartVO searchCart(Long userId){
        List<CartInfoDTO> cartInfoDTOList = cartMapper.searchCart(userId);
        Map<Long, List<CartInfoDTO>> cartInfoMap = new HashMap<>();
        List<CartInfoVO> cartInfoVOList = new ArrayList<>();
        for(CartInfoDTO cartInfoDTO : cartInfoDTOList){
            Long shopId = cartInfoDTO.getShopId();
            List<CartInfoDTO> cartInfoList;
            if(cartInfoMap.containsKey(shopId)){
                cartInfoList = cartInfoMap.get(shopId);
            }
            else {
                cartInfoList = new ArrayList<>();
            }
            cartInfoList.add(cartInfoDTO);
            cartInfoMap.put(shopId,cartInfoList);
        }
        for(Long key : cartInfoMap.keySet()){
            List<CartInfoDTO> cartList = cartInfoMap.get(key);
            CartInfoVO cartInfoVO = new CartInfoVO();
            cartInfoVO.setShopId(key);
            cartInfoVO.setShopCartInfoList(cartList);
            cartInfoVOList.add(cartInfoVO);
        }
        CartVO cartVO = new CartVO();
        cartVO.setCartInfoList(cartInfoVOList);
        return cartVO;
    }

    @Override
    public boolean addCartProduct(Cart cart){
        Cart oldCart = cartMapper.selectById(cart.getId());
        if(!(oldCart.getQuantity().equals(cart.getQuantity()) && oldCart.getProductId().equals(cart.getProductId()))){
            return false;
        }
        cart.setQuantity(cart.getQuantity() + 1);
        return cartMapper.updateById(cart) == 1;
    }

    @Override
    public boolean subCartProduct(Cart cart){
        Cart oldCart = cartMapper.selectById(cart.getId());
        if(!(oldCart.getQuantity().equals(cart.getQuantity()) && oldCart.getProductId().equals(cart.getProductId()))){
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

    @Override
    public Map<Long, List<OrderItem>> classifyByShop(List<Cart> cartList){
        Map<Long, List<OrderItem>> orderItemMap = new TreeMap<>();
        // 遍历cartList，按店铺分类生成初始订单项
        for(Cart cart: cartList){
            Product product = productMapper.selectById(cart.getProductId());
            Long shopId = product.getShopId();
            Long productId = product.getId();
            String productName = product.getName();
            String productImg = product.getImage();
            Integer unitPrice = product.getPrice();
            Integer quantity = cart.getQuantity();
            OrderItem orderItem = new OrderItem().setOrderId(-1L)
                    .setProductId(productId)
                    .setProductName(productName)
                    .setProductImg(productImg)
                    .setUnitPrice(unitPrice)
                    .setQuantity(quantity)
                    .setTotalPrice(unitPrice * quantity);
            // 写入map
            List<OrderItem> orderItemList;
            if(orderItemMap.containsKey(shopId)){
                orderItemList = orderItemMap.get(shopId);
            }
            else{
                orderItemList = new ArrayList<>();
            }
            orderItemList.add(orderItem);
            orderItemMap.put(shopId,orderItemList);
        }
        return orderItemMap;
    }

    @Override
    public void createOrder(Map<Long, List<OrderItem>> orderItemMap, List<OrderInfoDTO> orderInfoDTOList){
        // 提取数据
        String receiverAddress = orderInfoDTOList.get(0).getReceiverAddress();
        String receiverName = orderInfoDTOList.get(0).getReceiverName();
        String receiverMobile = orderInfoDTOList.get(0).getReceiverMobile();

        Map<Long,Integer> postageMap = getPostageMap(orderInfoDTOList);

        Set<Long> set = orderItemMap.keySet();
        // 遍历每个店铺的订单项集合
        for(Long key : set){
            List<OrderItem> thisOrderItemList = orderItemMap.get(key);
            Long userId = orderInfoDTOList.get(0).getUserId();
            Integer postage = postageMap.get(key);
            Order thisOrder = new Order().
                    setUserId(userId)
                    .setShopId(key)
                    .setReceiverAddress(receiverAddress)
                    .setReceiverName(receiverName)
                    .setReceiverMobile(receiverMobile)
                    .setPostage(postage)
                    .setPayTime(LocalDateTime.now());
            // 插入新order
            orderMapper.insert(thisOrder);
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Order::getUserId,userId).eq(Order::getTotalPrice,0);
            Order newOrder = orderMapper.selectOne(wrapper);
            Long orderId = newOrder.getId();
            // 遍历订单项，插入新订单项并更新订单总价
            for(OrderItem orderItem : thisOrderItemList){
                orderItem.setOrderId(orderId);
                newOrder.setTotalPrice(newOrder.getTotalPrice() + orderItem.getTotalPrice()); // 总价
                orderItemMapper.insert(orderItem);
            }
            orderMapper.updateById(newOrder);
        }
    }

    private Map<Long, Integer> getPostageMap(List<OrderInfoDTO> orderInfoDTOList){
        Map<Long, Integer> postageMap = new TreeMap<>();
        for(OrderInfoDTO orderInfoDTO : orderInfoDTOList){
            Integer postage = orderInfoDTO.getPostage();
            Long shopId = productMapper.selectById(orderInfoDTO.getProductId()).getShopId();
            postageMap.put(shopId,postage);
        }
        return postageMap;
    }
}

