package com.team07.online_shopping_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.domain.UserAddress;
import com.team07.online_shopping_mall.mapper.UserAddressMapper;
import com.team07.online_shopping_mall.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private UserAddressService userAddressService;

    public void updateAddress(UserAddress userAddress,String address){
        UpdateWrapper<UserAddress> userAddressUpdateWrapper = new UpdateWrapper<>();
        userAddressUpdateWrapper.eq("receiver_address", address);
        userAddressMapper.update(userAddress,userAddressUpdateWrapper);
    }
    public boolean setAddress(String address, int status){
        UserAddress userAddress = new UserAddress();
        userAddress.setStatus(status);
        userAddressService.updateAddress(userAddress,address);
        return true;
    }

    @Override
    public ApiRestResponse setDefaultUserAddress(Long userId, String address) {
        if (userAddressMapper.setDefaultUserAddress(userId,address)>=1)
            return ApiRestResponse.success();
        else
            return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
    }
}
