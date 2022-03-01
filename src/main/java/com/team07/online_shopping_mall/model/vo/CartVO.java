package com.team07.online_shopping_mall.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 查看购物车：二次封装，向前端传输的数据格式
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVO {
    private List<CartInfoVO> cartInfoList;
}
