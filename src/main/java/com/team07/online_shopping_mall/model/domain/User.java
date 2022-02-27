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
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mall_user")
@ApiModel(value="User对象", description="")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "用户名")
    private String username;

            @ApiModelProperty(value = "用户密码")
    private String password;

            @ApiModelProperty(value = "昵称")
    private String nickname;

            @ApiModelProperty(value = "头像")
    private String image;

            @ApiModelProperty(value = "0-买家 -1-卖家")
    private Integer role;

            @ApiModelProperty(value = "会员等级")
    private Integer membershipLevel;

            @ApiModelProperty(value = "积分")
    private Long integral;


    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
