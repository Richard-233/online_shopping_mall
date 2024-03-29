package com.team07.online_shopping_mall.service;

import com.team07.online_shopping_mall.model.domain.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
public interface ShopService extends IService<Shop> {

    boolean identifyUser(Long id, Long userId);
}
