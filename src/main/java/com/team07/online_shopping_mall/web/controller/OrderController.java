package com.team07.online_shopping_mall.web.controller;

import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.model.vo.OrderVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.service.OrderService;
import com.team07.online_shopping_mall.model.domain.Order;


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
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Order  order =  orderService.getById(id);
        return JsonResponse.success(order);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        orderService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateOrder(Order  order) throws Exception {
        orderService.updateById(order);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Order
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Order  order) throws Exception {
        orderService.save(order);
        return JsonResponse.success(null);
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
     * 描述:根据status查找用户订单
     * @Author: xy
     */
    @RequestMapping(value = "/searchUserOrderByStatus", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse searchUserOrderByStatus(@RequestParam Long userId, @RequestParam Integer status) throws Exception {
        OrderVO orderVO = orderService.searchUserOrderByStatus(userId, status);
        return ApiRestResponse.success(orderVO);
    }
}

