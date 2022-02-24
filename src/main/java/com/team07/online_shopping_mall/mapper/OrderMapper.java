package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

}
