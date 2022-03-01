package com.team07.online_shopping_mall.model.dto;

import lombok.Data;

@Data
public class CartInfoDTO {
    private Long cartId;
    private Long userId;
    private Long shopId;
    private Long productId;
    private String shopName;
    private String productName;
    private String productImg;
    private Integer price;
    private Integer quantity;
}
