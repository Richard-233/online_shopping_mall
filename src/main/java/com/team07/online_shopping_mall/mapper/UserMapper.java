package com.team07.online_shopping_mall.mapper;

import com.team07.online_shopping_mall.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author team07
 * @since 2022-02-24
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    User selectByName(String userName);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    User selectLogin(@Param("userName") String userName, @Param("password") String password);

    List<User> selectList();

}
