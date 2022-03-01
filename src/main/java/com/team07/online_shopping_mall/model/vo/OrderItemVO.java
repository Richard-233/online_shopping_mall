package com.team07.online_shopping_mall.model.vo;

import com.team07.online_shopping_mall.model.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemVO {
    private Long orderId;
    private List<OrderItemDTO> orderItemList;
}
