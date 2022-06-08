package com.team07.online_shopping_mall.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.mapper.UserAddressMapper;
import com.team07.online_shopping_mall.model.domain.Shop;
import com.team07.online_shopping_mall.model.domain.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.service.UserAddressService;
import com.team07.online_shopping_mall.model.domain.UserAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @GetMapping("/getId/{id}")
    @ResponseBody
    public ApiRestResponse getById(@PathVariable("id")Long id)throws MallException{
        UserAddress userAddress = userAddressService.getById(id);
        return ApiRestResponse.success(userAddress);
    }

    /**
     * 描述：根据UserId查询
     *
     */
    @GetMapping("/getByUserId")
    @ResponseBody
    public ApiRestResponse getByUserId(HttpSession session)throws MallException{
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        wrapper.eq("user_id",currentUser.getId());
        List<UserAddress> list= userAddressService.list(wrapper);
        if (list.size()>0)
            return ApiRestResponse.success(list);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }

    /**
     * 描述：查询所有
     *
     */
    @RequestMapping(value = "/getAddress", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getAllSuper()throws MallException{
        QueryWrapper<UserAddress> wrapper=new QueryWrapper<>();
        List<UserAddress> userAddresses=userAddressService.list(wrapper);
        if(userAddresses.size()>0)
            return ApiRestResponse.success(userAddresses);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }

    /**
    * 描述：根据Id删除(用户)，删除的请求方式为DELETE
    *
    */
    @DeleteMapping("/user/delete/{userId}")
    @ResponseBody
    public ApiRestResponse deleteById(@PathVariable("userId") Long userId, Long id, HttpServletResponse response) throws MallException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        userAddressService.removeById(id);
        return ApiRestResponse.success();
    }
    /**
     * 描述：根据Id删除(管理员)，删除的请求方式为DELETE
     *
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ApiRestResponse deleteById(@PathVariable("id")Long id) throws MallException {
        userAddressService.removeById(id);
        return ApiRestResponse.success();
    }


    /**
    * 描述：修改UserAddress,根据地址的id修改一个地址,感觉查询数据库太多次了
    *
    */
    @GetMapping("/updateUserAddress")
    @ResponseBody
    public ApiRestResponse updateUserAddress(Long id, String address, String name, String mobile) throws MallException {
        System.out.println(11111);
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        userAddress.setReceiverAddress(address);
        userAddress.setReceiverName(name);
        userAddress.setReceiverMobile(mobile);
        //System.out.println(userAddress);


        userAddress.setUserId(userAddressService.getById(id).getUserId());
        userAddress.setStatus(userAddressService.getById(id).getStatus());
        System.out.println(userAddress);
//        QueryWrapper<UserAddress> wrapper= new QueryWrapper<UserAddress>();
//        wrapper.eq("id",id);
//        userAddressService.update(wrapper);

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
    @ApiOperation(value = "设置一个默认的地址",notes = "传入那个默认的地址的id，如果已有默认地址会替换掉")
    public ApiRestResponse setDefaultUserAddress(Long id, HttpServletRequest request)throws MallException{
    //public ApiRestResponse setDefaultUserAddress(Long userId, String address)throws MallException{
        /*   原来的代码我注释掉了，感觉逻辑不清楚是怎么回事，一句sql就写完的事。。
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
        */
      return userAddressService.setDefaultUserAddress(id,((User)request.getSession().getAttribute(Constant.MALL_USER)).getId());


    }




    /**
     * 描述:设置UserAddress为家,这里感觉直接传的是地址名称，虽然我感觉可能传地址的id好些，毕竟地址名称也许会重复
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
     * 描述:设置UserAddress为学校，这里同理我觉得传id好些
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
     * 描述:设置UserAddress为公司，这里同理我觉得传id好些
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

