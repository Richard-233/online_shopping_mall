package com.team07.online_shopping_mall.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.mybatisplus.common.JsonResponse;
import com.team07.online_shopping_mall.service.ShopService;
import com.team07.online_shopping_mall.model.domain.Shop;


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
@RequestMapping("/api/shop")
public class ShopController {

    private final Logger logger = LoggerFactory.getLogger( ShopController.class );

    @Autowired
    private ShopService shopService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Shop  shop =  shopService.getById(id);
        return JsonResponse.success(shop);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        shopService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateShop(Shop  shop) throws Exception {
        shopService.updateById(shop);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Shop
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Shop  shop) throws Exception {
        shopService.save(shop);
        return JsonResponse.success(null);
    }
}

