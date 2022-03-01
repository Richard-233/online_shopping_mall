package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.model.domain.Shop;
import com.team07.online_shopping_mall.mapper.ShopMapper;
import com.team07.online_shopping_mall.service.ShopService;
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
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {
    @Autowired
    private ShopMapper shopMapper;
    public boolean identifyUser(Long id, Long userId){
        return userId.equals(id);
    }
}
