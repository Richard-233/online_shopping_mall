package com.team07.online_shopping_mall.web.controller;

import com.github.pagehelper.PageInfo;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.vo.OrderVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.service.OrderService;

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
@RequestMapping("/api/order")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger( OrderController.class );

    @Autowired
    private OrderService orderService;

    /**
     * 描述:管理员查找所有订单
     * @Author: xy
     */
    @RequestMapping(value = "/searchAllOrder", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse searchAllOrder() throws Exception {
        OrderVO orderVO = orderService.searchAllOrder();
        System.out.println("test12");
        return ApiRestResponse.success(orderVO);
    }

    /**
     * 描述:管理员根据状态查找订单
     * @Author: xy
     */
    @RequestMapping(value = "/searchOrderByStatus", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse searchOrderByStatus(@RequestParam Integer status) throws Exception {
        OrderVO orderVO = orderService.searchOrderByStatus(status);
        return ApiRestResponse.success(orderVO);
    }

    /**
     * 描述:查找用户所有订单
     * @Author: xy
     */
    @RequestMapping(value = "/searchUserAllOrder", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse searchUserAllOrder(@RequestParam Long userId) throws Exception {
        OrderVO orderVO = orderService.searchAllByUserId(userId);
        return ApiRestResponse.success(orderVO);
    }

    /**
     * 描述:查找店铺所有订单
     * @Author: xy
     */
    @RequestMapping(value = "/searchShopAllOrder", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse searchShopAllOrder(@RequestParam Long userId) throws Exception {
        OrderVO orderVO = orderService.searchShopAllOrder(userId);
        return ApiRestResponse.success(orderVO);
    }

    /**
     * 描述:根据status查找用户订单
     * @Author: xy
     */
    @RequestMapping(value = "/searchUserOrderByStatus", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse searchUserOrderByStatus(@RequestParam Long userId, @RequestParam Integer status) throws Exception {
        OrderVO orderVO = orderService.searchUserOrderByStatus(userId, status);
        return ApiRestResponse.success(orderVO);
    }

    /**
     * 描述:根据status查找店铺订单
     * @Author: xy
     */
    @RequestMapping(value = "/searchShopOrderByStatus", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse searchShopOrderByStatus(@RequestParam Long userId, @RequestParam Integer status) throws Exception {
        OrderVO orderVO = orderService.searchShopOrderByStatus(userId, status);
        return ApiRestResponse.success(orderVO);
    }

    /**
     * 描述: 买家更改订单状态
     * @Author: xy
     */
    @RequestMapping(value = "/buyerChangeOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse buyerChangeOrderStatus(@RequestParam Long orderId) throws Exception {
        if(orderService.buyerChangeOrderStatus(orderId)){
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
    }

    /**
     * 描述: 卖家更改订单状态
     * @Author: xy
     */
    @RequestMapping(value = "/sellerChangeOrderStatus", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse sellerChangeOrderStatus(@RequestParam Long orderId) throws Exception {
        if(orderService.sellerChangeOrderStatus(orderId)){
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
    }

    /**
     * 描述: 买家申请退款
     * @Author: xy
     */
    @RequestMapping(value = "/buyerRefund", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse buyerRefund(@RequestParam Long orderId) throws Exception {
        if(orderService.buyerRefund(orderId)){
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
    }

    /**
     * 描述: 卖家同意退款
     * @Author: xy
     */
    @RequestMapping(value = "/sellerAgreeRefund", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse sellerAgreeRefund(@RequestParam Long orderId) throws Exception {
        if(orderService.sellerAgreeRefund(orderId)){
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
    }

    /**
     * 描述: 卖家拒绝退款
     * @Author: xy
     */
    @RequestMapping(value = "/sellerRefuseRefund", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse sellerRefuseRefund(@RequestParam Long orderId) throws Exception {
        if(orderService.sellerRefuseRefund(orderId)){
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
    }
}

