package com.team07.online_shopping_mall.model.vo;

import com.team07.online_shopping_mall.model.dto.OrderInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoVO {
   private   List<OrderInfoDTO> orderInfoList;
}
