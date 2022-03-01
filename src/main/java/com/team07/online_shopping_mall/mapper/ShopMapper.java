package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Repository
public interface ShopMapper extends BaseMapper<Shop> {

    List<Shop> getByName(String name);

    List<Shop> getByNameSuper(String name);

    //boolean identifyUser(Long id, Long userId);
}
