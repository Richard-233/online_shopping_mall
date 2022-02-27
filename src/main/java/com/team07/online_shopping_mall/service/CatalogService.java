package com.team07.online_shopping_mall.service;

import com.team07.online_shopping_mall.model.domain.Catalog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team07.online_shopping_mall.model.request.AddCategoryReq;

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
}
