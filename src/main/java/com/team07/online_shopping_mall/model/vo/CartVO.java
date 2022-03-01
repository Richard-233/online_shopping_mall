package com.team07.online_shopping_mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVO {
    private List<CartInfoVO> cartInfoList;
}
