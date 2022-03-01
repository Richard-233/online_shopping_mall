package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.mapper.UserMapper;
import com.team07.online_shopping_mall.model.domain.User;
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

}
