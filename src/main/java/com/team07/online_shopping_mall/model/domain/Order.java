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
 * 订单
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_order")
@ApiModel(value="Order对象", description="订单")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "订单号（非主键id）")
    private String orderNo;

            @ApiModelProperty(value = "用户id")
    private Long userId;

            @ApiModelProperty(value = "商店id")
    private Long shopId;

            @ApiModelProperty(value = "订单总价格")
    private Integer totalPrice;

            @ApiModelProperty(value = "收货人姓名快照")
    private String receiverName;

            @ApiModelProperty(value = "收货人手机号快照")
    private String receiverMobile;

            @ApiModelProperty(value = "收获地址快照")
    private String receiverAddress;

            @ApiModelProperty(value = "订单状态：")
    private Integer orderStatus;

            @ApiModelProperty(value = "运费，默认为0")
    private Integer postage;

            @ApiModelProperty(value = "支付类型：1-支付宝")
    private Integer paymentType;

            @ApiModelProperty(value = "发货时间")
    private LocalDateTime deliveryTime;

            @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

            @ApiModelProperty(value = "交易完成时间")
    private LocalDateTime endTime;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
