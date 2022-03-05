package com.team07.online_shopping_mall.common.utls;

import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.model.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityUtils {
    /**
     * 获取当前用户
     *
     * @return
     */
    public static User getCurrentUserInfo() {
        User userInfo = SessionUtils.getCurrentUserInfo();
        //模拟登录
        if (userInfo == null) {
            userInfo = new User();
            userInfo.setUsername("模拟");
        }

        return userInfo;
    }

    public static UserInfoDTO getUserInfo() {
        User userInfo = SessionUtils.getCurrentUserInfo();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        //模拟登录
        if (userInfo == null) {
            userInfo = new User();
            userInfo.setUsername("模拟用户");
            userInfoDTO.setId(106L);
            userInfoDTO.setName("模拟用户");
            userInfoDTO.setUserType(1L);
        }else{
            userInfoDTO.setId(106L);
            userInfoDTO.setName("模拟用户");
            userInfoDTO.setUserType(1L);
        }

        return userInfoDTO;
    }
}
