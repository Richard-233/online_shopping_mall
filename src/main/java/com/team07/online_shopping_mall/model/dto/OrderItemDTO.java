package com.team07.online_shopping_mall.model.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long orderItemId;
    private Long productId;
    private Long orderId;
    private Long userId;
    private Long shopId;

    private String productImg;
    private Integer unitPrice;
    private Integer quantity;
    private Integer itemTotalPrice;

    private String shopName;

    private Integer postage;
    private Integer orderTotalPrice;
    private String receiverName;
    private String receiverMobile;
    private String receiverAddress;
    private Integer orderStatus;
}
