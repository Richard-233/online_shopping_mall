package com.team07.online_shopping_mall.model.vo;

import com.team07.online_shopping_mall.model.dto.CartInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 查看购物车：一次封装
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartInfoVO {
    private Long shopId;
    private String shopName;
    private List<CartInfoDTO> shopCartInfoList;
}
