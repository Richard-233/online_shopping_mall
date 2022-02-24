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
 * 目录
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_catalog")
@ApiModel(value="Catalog对象", description="目录")
public class Catalog extends Model<Catalog> {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "分类目录名称")
    private String name;

            @ApiModelProperty(value = "分类目录级别 1代表1一级")
    private Integer type;

            @ApiModelProperty(value = "父id")
    private Long parentId;

            @ApiModelProperty(value = "目录展示时的顺序")
    private Integer orderNum;

            @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
