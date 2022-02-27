package com.team07.online_shopping_mall.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author team07
 * @since 2022-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_order_item")
@ApiModel(value="OrderItem对象", description="")
public class OrderItem extends Model<OrderItem> {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

            @ApiModelProperty(value = "商品id")
    private Long productId;

            @ApiModelProperty(value = "商品名称")
    private String productName;

            @ApiModelProperty(value = "商品图片")
    private String productImg;

            @ApiModelProperty(value = "单价（下单时的价格）")
    private Integer unitPrice;

            @ApiModelProperty(value = "商品数量")
    private Integer quantity;

            @ApiModelProperty(value = "商品总价")
    private Integer totalPrice;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
