package com.example.mybatisplus;

import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisplusApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
        User byId = userService.getById(1);
        System.out.println(byId);
    }

}
