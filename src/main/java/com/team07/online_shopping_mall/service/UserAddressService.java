package com.team07.online_shopping_mall.service;

import com.team07.online_shopping_mall.model.domain.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
public interface UserAddressService extends IService<UserAddress> {
    void updateAddress(UserAddress userAddress, String address);
    boolean setAddress(String address, int status);
}
