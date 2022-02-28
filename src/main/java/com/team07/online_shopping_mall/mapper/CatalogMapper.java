package com.team07.online_shopping_mall.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.team07.online_shopping_mall.model.domain.Catalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    Catalog selectByName(String name);

    int insertSelective(Catalog record);

    int updateByPrimaryKeySelective(Catalog record);

    Catalog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    List<Catalog> selectList();

    List<Catalog> selectCategoriesByParentId(Long parentId);
}
