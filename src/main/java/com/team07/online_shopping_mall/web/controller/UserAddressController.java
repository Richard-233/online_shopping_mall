package com.team07.online_shopping_mall.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.mapper.UserAddressMapper;
import com.team07.online_shopping_mall.model.domain.User;
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
    @RequestMapping(value = "createUserAddress", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse createUserAddress(@RequestBody UserAddress userAddress) throws MallException {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("receiver_address",userAddress.getReceiverAddress()).eq("user_id",userAddress.getUserId()).eq("receiver_name",userAddress.getReceiverName()).eq("receiver_mobile",userAddress.getReceiverMobile());
        List<UserAddress> list = userAddressService.list(wrapper);
        if(list.size()==0) {
            userAddressService.save(userAddress);
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(MallExceptionEnum.ADD_FAILED);
        }
    }


    /**
    * 描述：根据Id查询
    *
    */
    @GetMapping("getId/{id}")
    @ResponseBody
    public ApiRestResponse getById(@PathVariable("id")Long id)throws MallException{
        UserAddress userAddress = userAddressService.getById(id);
        return ApiRestResponse.success(userAddress);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @DeleteMapping("delete/{userId}")
    @ResponseBody
    public ApiRestResponse deleteById(@PathVariable("userId") Long userId,Long id) throws MallException {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        userAddressService.removeById(id);
        return ApiRestResponse.success();
    }


    /**
    * 描述：修改UserAddress
    *
    */
    @PostMapping("/updateUserAddress/{id}")
    @ResponseBody
    public ApiRestResponse updateUserAddress(@PathVariable("id") Long id,@RequestParam("receiverAddress") String address,@RequestParam("receiverName") String name,@RequestParam("receiverMobile") String mobile) throws MallException {
        UserAddress userAddress = new UserAddress();
        userAddress.setReceiverAddress(address);
        userAddress.setReceiverName(name);
        userAddress.setReceiverMobile(mobile);

        UpdateWrapper<UserAddress> userAddressUpdateWrapper = new UpdateWrapper<>();
        userAddressUpdateWrapper.eq("id", id);
        userAddressMapper.update(userAddress, userAddressUpdateWrapper);
        return ApiRestResponse.success();
    }





    /**
     * 描述:设置UserAddress为默认地址
     *
     */
    @PostMapping("/setDefaultAddress")
    @ResponseBody
    public ApiRestResponse setDefaultUserAddress(Long userId, String address)throws MallException{
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("status",4).eq("user_id",userId);
        List<UserAddress> list = userAddressService.list(wrapper);

        UserAddress userAddress = new UserAddress();
        if(list.size()==1){
            list.get(0).setStatus(0);
            userAddressService.updateAddress(list.get(0),list.get(0).getReceiverAddress());
            userAddress.setStatus(4);
            userAddressService.updateAddress(userAddress,address);
            return ApiRestResponse.success();
        }else if(list.size()==0){
            userAddress.setStatus(4);
            userAddressService.updateAddress(userAddress,address);
            return ApiRestResponse.success();
        }
        else {
            return ApiRestResponse.error(MallExceptionEnum.SET_DEFAULT_ADDRESS_FAILED);
        }
    }




    /**
     * 描述:设置UserAddress为家
     *
     */
    @PostMapping("/setHomeAddress")
    @ResponseBody
    public ApiRestResponse setHomeAddress(String address)throws MallException{
        if(userAddressService.setAddress(address,1)){
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(MallExceptionEnum.SET_HOME_ADDRESS_FAILED);
        }
    }


    /**
     * 描述:设置UserAddress为学校
     *
     */
    @PostMapping("/setSchoolAddress")
    @ResponseBody
    public ApiRestResponse setSchoolAddress(String address)throws MallException{
        if(userAddressService.setAddress(address,2)){
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(MallExceptionEnum.SET_SCHOOL_ADDRESS_FAILED);
        }
    }


    /**
     * 描述:设置UserAddress为公司
     *
     */
    @PostMapping("/setCompanyAddress")
    @ResponseBody
    public ApiRestResponse setCompanyAddress(String address)throws MallException{
        if(userAddressService.setAddress(address,3)){
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(MallExceptionEnum.SET_COMPANY_ADDRESS_FAILED);
        }
    }

}

