package com.team07.online_shopping_mall.service;

import com.github.pagehelper.PageInfo;
import com.team07.online_shopping_mall.model.domain.Catalog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team07.online_shopping_mall.model.request.AddCategoryReq;
import com.team07.online_shopping_mall.model.vo.CatalogVO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 目录 服务类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
public interface CatalogService extends IService<Catalog> {

    void add(AddCategoryReq addCategoryReq);

    void update(Catalog updateCategory);

    void delete(Long id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CatalogVO> listCategoryForCustom(Long parentId);
}
