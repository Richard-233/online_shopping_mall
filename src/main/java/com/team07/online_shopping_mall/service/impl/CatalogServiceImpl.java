package com.team07.online_shopping_mall.service.impl;

import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.domain.Catalog;
import com.team07.online_shopping_mall.mapper.CatalogMapper;
import com.team07.online_shopping_mall.model.request.AddCategoryReq;
import com.team07.online_shopping_mall.service.CatalogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 目录 服务实现类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog> implements CatalogService {

    @Autowired
    CatalogMapper catalogMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Catalog catalog = new Catalog();
        BeanUtils.copyProperties(addCategoryReq, catalog);
        Catalog catalogOld = catalogMapper.selectByName(addCategoryReq.getName());
        if (catalogOld != null) {
            throw new MallException(MallExceptionEnum.NAME_EXISTED);
        }
        int count = catalogMapper.insertSelective(catalog);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.CREATE_FAILED);
        }
    }
}
