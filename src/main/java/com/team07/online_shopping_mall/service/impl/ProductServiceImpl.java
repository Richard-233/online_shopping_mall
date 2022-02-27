package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.model.domain.Product;
import com.team07.online_shopping_mall.mapper.ProductMapper;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    ProductMapper productMapper;

    public boolean identifyUser(Long userId, Long shopId){
        List<Long> list=new ArrayList<>();
        list.add(shopId);
        list.add(productMapper.getShopIdByUserId(userId));
        if(list.get(0).equals(list.get(1)))
        return true;
        else return false;
    }

}
