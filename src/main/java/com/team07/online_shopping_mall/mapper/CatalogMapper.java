package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.Catalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 目录 Mapper 接口
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Repository
public interface CatalogMapper extends BaseMapper<Catalog> {

}
