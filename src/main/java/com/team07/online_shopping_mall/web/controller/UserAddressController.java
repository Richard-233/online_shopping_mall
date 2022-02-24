package com.team07.online_shopping_mall.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.service.UserAddressService;
import com.team07.online_shopping_mall.model.domain.UserAddress;


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
@RequestMapping("/api/userAddress")
public class UserAddressController {

    private final Logger logger = LoggerFactory.getLogger( UserAddressController.class );

    @Autowired
    private UserAddressService userAddressService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        UserAddress  userAddress =  userAddressService.getById(id);
        return JsonResponse.success(userAddress);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        userAddressService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateUserAddress(UserAddress  userAddress) throws Exception {
        userAddressService.updateById(userAddress);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建UserAddress
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(UserAddress  userAddress) throws Exception {
        userAddressService.save(userAddress);
        return JsonResponse.success(null);
    }
}

