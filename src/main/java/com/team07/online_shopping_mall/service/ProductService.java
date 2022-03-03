package com.team07.online_shopping_mall.service;

import com.github.pagehelper.PageInfo;
import com.team07.online_shopping_mall.model.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team07.online_shopping_mall.model.request.ProductListReq;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Repository
public interface ProductService extends IService<Product> {
    boolean identifyUser(Long userId, Long shopId);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    PageInfo listForSeller(Integer pageNum, Integer pageSize, Long currentUserId);

    PageInfo list(ProductListReq productListReq);
}
