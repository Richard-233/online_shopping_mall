package com.team07.online_shopping_mall.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_shop")
@ApiModel(value="Shop对象", description="")
public class Shop extends Model<Shop> {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "店铺名称")
    private String name;

            @ApiModelProperty(value = "店主id")
    private Long userId;

            @ApiModelProperty(value = "店铺简介")
    private String intro;

            @ApiModelProperty(value = "店铺地址")
    private String address;

    private String phone;

            @ApiModelProperty(value = "店铺类别id")
    private Long catalogId;

            @ApiModelProperty(value = "店铺图片")
    private String image;

            @ApiModelProperty(value = "店铺评价")
    private Integer score;

            @ApiModelProperty(value = "店铺是否下架 -1-下架 0-上线")
    private Integer offline;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
