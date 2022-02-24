package com.team07.online_shopping_mall.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.team07.online_shopping_mall.service.OrderItemService;
import com.team07.online_shopping_mall.model.domain.OrderItem;


/**
 *
 *  前端控制器
 *
 *
 * @author team07
 * @since 2022-02-24
 * @version v1.0
 */
@Controller
@RequestMapping("/api/orderItem")
public class OrderItemController {

    private final Logger logger = LoggerFactory.getLogger( OrderItemController.class );

    @Autowired
    private OrderItemService orderItemService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        OrderItem  orderItem =  orderItemService.getById(id);
        return JsonResponse.success(orderItem);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        orderItemService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateOrderItem(OrderItem  orderItem) throws Exception {
        orderItemService.updateById(orderItem);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建OrderItem
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(OrderItem  orderItem) throws Exception {
        orderItemService.save(orderItem);
        return JsonResponse.success(null);
    }
}

