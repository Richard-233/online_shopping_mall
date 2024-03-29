package com.team07.online_shopping_mall.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 购物车
 * </p>
 *
 * @author team07
 * @since 2022-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_cart")
@ApiModel(value="Cart对象", description="购物车")
public class Cart extends Model<Cart> {

    private static final long serialVersionUID = 1L;
            @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "用户id")
    private Long userId;

            @ApiModelProperty(value = "商品id")
    private Long productId;

            @ApiModelProperty(value = "商品数量")
    private Integer quantity;

            @ApiModelProperty(value = "是否选择")
    private boolean selected;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
