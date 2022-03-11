package com.team07.online_shopping_mall.service;

import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Service
public interface UserService extends IService<User> {
    void register(String userName, String password) throws MallException;

    User login(String userName, String password) throws MallException;

    void updateInfo(User user) throws MallException;

    boolean checkRole(User user);

    boolean checkAdminRole(User user);

    List<User> userList();
}
