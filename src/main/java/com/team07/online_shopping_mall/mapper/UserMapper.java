package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
