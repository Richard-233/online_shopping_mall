package com.team07.online_shopping_mall.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team07.online_shopping_mall.common.ApiRestReasponse;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.service.CartService;
import com.team07.online_shopping_mall.model.domain.Cart;

import java.util.List;


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
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Cart  cart =  cartService.getById(id);
        return JsonResponse.success(cart);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        cartService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateCart(Cart  cart) throws Exception {
        cartService.updateById(cart);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Cart
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Cart  cart) throws Exception {
        cartService.save(cart);
        return JsonResponse.success(null);
    }

    /**
     * 描述:从店铺添加商品至购物车,若购物车存在该商品则更新数量，若不存在则添加至购物车
     * 参数：Cart cart(含productId,quantity,userId)
     * @Author: xy
     * @Return: 成功/失败信息
     */
    @RequestMapping("/addToCart")
    @ResponseBody
    public ApiRestReasponse addToCart(@RequestBody Cart cart) throws Exception{
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Cart::getUserId,cart.getUserId()).eq(Cart::getProductId,cart.getProductId());
        List<Cart> lists = cartService.list(wrapper);

        if(lists.size() == 1){
            Cart currentCart = lists.get(0);
            currentCart.setQuantity(currentCart.getQuantity() + cart.getQuantity());
            if(cartService.updateById(currentCart)){
                return ApiRestReasponse.success();
            }
            else {
                return ApiRestReasponse.error(MallExceptionEnum.UPDATE_FAILED);
            }
        }
        else if(cartService.save(cart)){
            return ApiRestReasponse.success();
        }
        else {
            return ApiRestReasponse.error(MallExceptionEnum.INSERT_FAILED);
        }
    }

    /**
     * 描述:在购物车中增加商品数量
     * 参数：Cart cart(含id，productId,quantity)
     * @Author: xy
     * @Return: 成功/失败信息
     */
    @RequestMapping("/addCartProducts")
    @ResponseBody
    public ApiRestReasponse addCartProducts(@RequestBody Cart cart) throws Exception{
        if(cartService.addCartProduct(cart)){
            return ApiRestReasponse.success();
        }
        else {
            return ApiRestReasponse.error(MallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 描述:在购物车中减少商品数量/删除商品
     * 参数：Cart cart(含id，productId,quantity)
     * @Author: xy
     * @Return: 成功/失败信息
     */
    @RequestMapping("/subCartProducts")
    @ResponseBody
    public ApiRestReasponse subCartProducts(@RequestBody Cart cart) throws Exception{
        System.out.println(cart);
        if(cartService.subCartProduct(cart)){
            return ApiRestReasponse.success();
        }
        else {
            return ApiRestReasponse.error(MallExceptionEnum.UPDATE_FAILED);
        }
    }



}

