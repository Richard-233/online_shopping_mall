package com.team07.online_shopping_mall.model.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 商品表
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_product")
@ApiModel(value="Product对象", description="商品表")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "商品主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "店铺id")
    private Long shopId;

            @ApiModelProperty(value = "商品图片路径地址")
    private String image;

    @ApiModelProperty(value = "商品详情")
    private String detail;

            @ApiModelProperty(value = "分类id")
    private Long catalogId;

            @ApiModelProperty(value = "价格 单位-分")
    private Integer price;

            @ApiModelProperty(value = "库存数量")
    private Integer stock;

            //0是下架  1是上架  2是推荐
            @ApiModelProperty(value = "商品上架状态")
    private Integer status;


            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
