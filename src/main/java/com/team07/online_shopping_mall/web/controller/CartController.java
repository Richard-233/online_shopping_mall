package com.team07.online_shopping_mall.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.domain.OrderItem;
import com.team07.online_shopping_mall.model.dto.OrderInfoDTO;
import com.team07.online_shopping_mall.model.vo.CartVO;
import com.team07.online_shopping_mall.model.vo.OrderInfoVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.service.CartService;
import com.team07.online_shopping_mall.model.domain.Cart;

import java.util.*;


/**
 *
 *  前端控制器
 *
 *
 * @author team07
 * @since 2022-02-27
 * @version v1.0
 */
@Controller
@RequestMapping("/api/cart")
public class CartController {

    private final Logger logger = LoggerFactory.getLogger( CartController.class );

    @Autowired
    private CartService cartService;


    /**
     * 描述: 获取购物车商品
     * 参数：Long userId
     * @Author: xy
     * @Return: cartVO
     */
    @RequestMapping(value = "/searchCart", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse searchCart(@RequestParam Long userId) throws Exception{
        CartVO cartVO = cartService.searchCart(userId);
        return ApiRestResponse.success(cartVO);
    }

    /**
     * 描述:从店铺添加商品至购物车,若购物车存在该商品则更新数量，若不存在则添加至购物车
     * 参数：Cart cart(含productId,quantity,userId)
     * @Author: xy
     * @Return: 成功/失败信息
     */
    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse addToCart(@RequestBody Cart cart) throws Exception {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Cart::getUserId, cart.getUserId()).eq(Cart::getProductId, cart.getProductId());
        List<Cart> lists = cartService.list(wrapper);

        if (lists.size() == 1) {
            Cart currentCart = lists.get(0);
            currentCart.setQuantity(currentCart.getQuantity() + cart.getQuantity());
            if (cartService.updateById(currentCart)) {
                return ApiRestResponse.success();
            }
            else {
                return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
            }
        }
        else if(cartService.save(cart)) {
            return ApiRestResponse.success();
        }
        else {
            return ApiRestResponse.error(MallExceptionEnum.INSERT_FAILED);
        }
    }

    /**
     * 描述:在购物车中增加商品数量
     * 参数：Cart cart(含id，productId,quantity)
     * @Author: xy
     * @Return: 成功/失败信息
     */
    @RequestMapping(value = "/addCartProducts", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse addCartProducts(@RequestBody Cart cart) throws Exception{
        if(cartService.addCartProduct(cart)){
            return ApiRestResponse.success();
        }
        else {
            return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 描述:在购物车中减少商品数量/删除商品
     * 参数：Cart cart(含id，productId,quantity)
     * @Author: xy
     * @Return: 成功/失败信息
     */
    @RequestMapping(value = "/subCartProducts", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse subCartProducts(@RequestBody Cart cart) throws Exception {
        System.out.println(cart);
        if (cartService.subCartProduct(cart)) {
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 描述:将购物车中选中商品添加至订单
     * 参数：OrderInfoVO orderInfoVO(参考OrderInfoDTO全部参数)
     * @Author: xy
     * @Return: 成功/失败信息
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse createOrder(@RequestBody OrderInfoVO orderInfoVO) throws Exception {
        List<OrderInfoDTO> orderInfoList = orderInfoVO.getOrderInfoList();
        System.out.println(orderInfoList);
        List<Cart> cartList = new ArrayList<>();
        for (OrderInfoDTO orderInfoDTO : orderInfoList) {
            Cart cart = new Cart().setId(orderInfoDTO.getCartId())
                    .setProductId(orderInfoDTO.getProductId())
                    .setQuantity(orderInfoDTO.getQuantity());
            cartList.add(cart);
        }
        // 按店铺分类为订单项
        Map<Long,List<OrderItem>> orderItemMap = cartService.classifyByShop(cartList);

        // 生成订单与订单项
        cartService.createOrder(orderItemMap,orderInfoList);
        // 从购物车中移除
        for(Cart cart : cartList){
            cartService.removeById(cart.getId());
        }
        return ApiRestResponse.success();
    }
}

