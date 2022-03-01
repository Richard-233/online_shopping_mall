package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team07.online_shopping_mall.model.dto.CartInfoDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 *
 * @author team07
 * @since 2022-02-27
 */
@Repository
public interface CartMapper extends BaseMapper<Cart> {

    List<CartInfoDTO> searchCart(Long userId);
}
