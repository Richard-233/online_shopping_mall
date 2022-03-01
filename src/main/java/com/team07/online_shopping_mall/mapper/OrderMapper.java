package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team07.online_shopping_mall.model.dto.OrderItemDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author team07
 * @since 2022-02-27
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    List<OrderItemDTO> searchAllByUserId(Long userId);

    List<OrderItemDTO> searchUserOrderByStatus(Long userId, Integer status);
}
