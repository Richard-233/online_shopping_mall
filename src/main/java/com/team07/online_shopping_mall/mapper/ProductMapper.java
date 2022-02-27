package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team07.online_shopping_mall.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> getByName(String name);

    //List<Long> identifyUser(@Param("user") User user,@Param("product") Product product);


    Long getShopIdByUserId(Long userId);
}
