package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.model.query.ProductListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

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

    Long getShopIdByUserId(Long userId);

    List<Product> getByNameSuper(String name);

    int batchUpdateSellStatus(@Param("ids") Integer[] ids, @Param("sellStatus") Integer sellStatus);

    List<Product> selectListForAdmin();

    List<Product> selectListForSeller(@Param("userId") Long currentUserId);

    Product selectByPrimaryKey(Long id);

    List<Product> selectList(@Param("query") ProductListQuery query);

}
