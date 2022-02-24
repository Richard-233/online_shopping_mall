package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.mapper.UserMapper;
import com.team07.online_shopping_mall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
