package com.team07.online_shopping_mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.mapper.ShopMapper;
import com.team07.online_shopping_mall.model.domain.Product;
import com.team07.online_shopping_mall.mapper.ProductMapper;
import com.team07.online_shopping_mall.model.query.ProductListQuery;
import com.team07.online_shopping_mall.model.request.ProductListReq;
import com.team07.online_shopping_mall.model.vo.CatalogVO;
import com.team07.online_shopping_mall.service.CatalogService;
import com.team07.online_shopping_mall.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


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
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    CatalogService catalogService;

    @Override
    public boolean identifyUser(Long userId, Long shopId) {
        return userId.equals(shopMapper.selectById(shopId).getUserId());
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) {
        productMapper.batchUpdateSellStatus(ids, sellStatus);
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    @Override
    public PageInfo listForSeller(Integer pageNum, Integer pageSize, Long currentUserId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectListForSeller(currentUserId);
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    @Override
    public PageInfo list(ProductListReq productListReq) {
        ProductListQuery productListQuery = new ProductListQuery();
        if (!StringUtils.isEmpty(productListReq.getKeyword())) {
            String keyword = new StringBuilder().append("%").append(productListReq.getKeyword()).append("%").toString();
            productListQuery.setKeyword(keyword);
        }
        if (productListReq.getCatalogId() != null) {
            List<CatalogVO> catalogVOList = catalogService.listCategoryForCustom(productListReq.getCatalogId());
            ArrayList<Long> categoryIds = new ArrayList<>();
            categoryIds.add(productListReq.getCatalogId());
            getCategoryIds(catalogVOList, categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }
        if (productListReq.getShopId() != null) {
            productListQuery.setShopId(productListReq.getShopId());
        }
        String orderBy = productListReq.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize(), orderBy);
        } else {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize());
        }
        List<Product> productList = productMapper.selectList(productListQuery);
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }

    private void getCategoryIds(List<CatalogVO> catalogVOList, ArrayList<Long> categoryIds) {
        for (int i = 0; i < catalogVOList.size(); i++) {
            CatalogVO catalogVO = catalogVOList.get(i);
            if (catalogVO != null) {
                categoryIds.add(catalogVO.getId());
                getCategoryIds(catalogVO.getChildCatalog(), categoryIds);
            }
        }
    }
}
