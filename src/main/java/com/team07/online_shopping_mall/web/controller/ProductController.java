package com.team07.online_shopping_mall.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team07.online_shopping_mall.mapper.ProductMapper;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.service.ProductService;
import com.team07.online_shopping_mall.model.domain.Product;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  前端控制器
 *
 *
 * @author team07
 * @since 2022-02-24
 * @version v1.0
 */
@Controller
@RequestMapping("/api/product")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger( ProductController.class );

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserService userService;

    /**
    * 描述：根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Product  product =  productService.getById(id);
        return JsonResponse.success(product);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@RequestParam("user") User user, @RequestParam("id") Long id) throws Exception {
        if(productService.identifyUser(user,productService.getById(id).getShopId())){
            productService.removeById(id);
        }
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateProduct(@RequestParam("user") User user, @RequestParam("product") Product  product) throws Exception {
//        User user1 =userService.getById(3);
//        Product product1=productService.getById(2);
//        product1.setDetail("非常好喝1");
        //product1.setId();
        if(productService.identifyUser(user,product.getShopId())){
            productService.updateById(product);
            //System.out.println(1);
        }
        return JsonResponse.success(null);
    }




    /**
     * 描述：根据product_name 查询所有
     *
     */
    @RequestMapping(value = "/name")
    @ResponseBody
    public JsonResponse getByName(@RequestParam String name)throws Exception {
        List<Product> lists=productMapper.getByName(name);
        return JsonResponse.success(lists);
    }





    /**
    * 描述:创建Product
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Product  product) throws Exception {
//        Product product2=new Product();
//        product2=productService.getById(2L);
        productService.save(product);
        return JsonResponse.success(null);
    }
}

