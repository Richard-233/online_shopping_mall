package com.example.mybatisplus;

//import com.example.mybatisplus.model.domain.Admin;
//import com.example.mybatisplus.service.AdminService;
import com.team07.online_shopping_mall.MybatisplusApplication;
import com.team07.online_shopping_mall.mapper.ProductMapper;
import com.team07.online_shopping_mall.model.domain.Product;
import com.team07.online_shopping_mall.service.ProductService;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = MybatisplusApplication.class)
class MybatisplusApplicationTests {

//    @Autowired
//    private AdminService adminService;
//    @Test
//    void contextLoads() {
//        Admin byId = adminService.getById(1);
//        System.out.println(byId);
//    }


    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void mySelect(){

        System.out.println(productService.getById(1));
    }

//    @Test
//    public void selectByName(){
//
//        List<Product> lists=productMapper.getByName("茅台");
//        System.out.println(lists);
//    private UserService userService;
//    @Test
//    void contextLoads() {
//        User byId = userService.getById(1);
//        System.out.println(byId);
//    }

}
