package com.team07.online_shopping_mall.service;

import com.team07.online_shopping_mall.model.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team07.online_shopping_mall.model.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Repository
public interface ProductService extends IService<Product> {
    boolean identifyUser(User user, Long shopId);

    //List<Product> getByName(String name);
}
