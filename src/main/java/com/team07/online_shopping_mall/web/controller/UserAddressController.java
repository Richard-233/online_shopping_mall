package com.team07.online_shopping_mall.web.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.mapper.UserAddressMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.service.UserAddressService;
import com.team07.online_shopping_mall.model.domain.UserAddress;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.List;
import java.util.Objects;


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
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 描述:创建UserAddress
     *
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse createUserAddress(UserAddress userAddress) throws Exception {
        userAddressService.save(userAddress);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id查询
    *
    */
    @GetMapping("/{id}")
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        UserAddress  userAddress =  userAddressService.getById(id);
        return JsonResponse.success(userAddress);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @DeleteMapping("/{id}")
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        userAddressService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：更改UserAddress
    *
    */
    @PostMapping("/updateUserAddress/{id}")
    @ResponseBody
    public JsonResponse updateUserAddress(@PathVariable("id") Long id,@RequestParam("receiverAddress") String address,@RequestParam("receiverName") String name,@RequestParam("receiverMobile") String mobile) throws Exception {
        UserAddress userAddress = new UserAddress();
        userAddress.setReceiverAddress(address);
        userAddress.setReceiverName(name);
        userAddress.setReceiverMobile(mobile);

        UpdateWrapper<UserAddress> userAddressUpdateWrapper = new UpdateWrapper<>();
        userAddressUpdateWrapper.eq("id",id);
        userAddressMapper.update(userAddress,userAddressUpdateWrapper);
        return JsonResponse.success(null);
    }





    /**
     * 描述:设置UserAddress为默认地址
     *
     */
    @PostMapping("/setDefaultAddress")
    @ResponseBody
    public JsonResponse setDefaultUserAddress(String address)throws Exception{
        UserAddress userAddress = new UserAddress();
        userAddress.setStatus(4);
        UpdateWrapper<UserAddress> userAddressUpdateWrapper = new UpdateWrapper<>();
        userAddressUpdateWrapper.eq("receiver_address",address);
        userAddressMapper.update(userAddress,userAddressUpdateWrapper);
        return JsonResponse.success(null);
    }


    /**
     * 描述:设置UserAddress为家
     *
     */
    @PostMapping("/setHomeAddress")
    @ResponseBody
    public JsonResponse setHomeAddress(String address)throws Exception{
        UserAddress userAddress = new UserAddress();
        userAddress.setStatus(3);
        UpdateWrapper<UserAddress> userAddressUpdateWrapper = new UpdateWrapper<>();
        userAddressUpdateWrapper.eq("receiver_address",address);
        userAddressMapper.update(userAddress,userAddressUpdateWrapper);
        return JsonResponse.success(null);
    }


    /**
     * 描述:设置UserAddress为学校
     *
     */
    @PostMapping("/setSchoolAddress")
    @ResponseBody
    public JsonResponse setSchoolAddress(String address)throws Exception{
        UserAddress userAddress = new UserAddress();
        userAddress.setStatus(3);
        UpdateWrapper<UserAddress> userAddressUpdateWrapper = new UpdateWrapper<>();
        userAddressUpdateWrapper.eq("receiver_address",address);
        userAddressMapper.update(userAddress,userAddressUpdateWrapper);
        return JsonResponse.success(null);
    }


    /**
     * 描述:设置UserAddress为公司
     *
     */
    @PostMapping("/setCompanyAddress")
    @ResponseBody
    public JsonResponse setCompanyAddress(String address)throws Exception{
        UserAddress userAddress = new UserAddress();
        userAddress.setStatus(3);
        UpdateWrapper<UserAddress> userAddressUpdateWrapper = new UpdateWrapper<>();
        userAddressUpdateWrapper.eq("receiver_address",address);
        userAddressMapper.update(userAddress,userAddressUpdateWrapper);
        return JsonResponse.success(null);
    }

}

