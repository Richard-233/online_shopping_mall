package com.team07.online_shopping_mall.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team07.online_shopping_mall.common.Constant;
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

import javax.servlet.http.HttpSession;
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
    * 描述：普通用户根据Id 查询
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id)throws Exception {
        Product  product =  productService.getById(id);
        if(product.getStatus()!=0)
        return JsonResponse.success(product);
        else
            return JsonResponse.failure("无");
    }

    /**
     * 描述：管理员根据Id 查询
     *
     */
    @RequestMapping(value = "/super/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getByIdSuper(@PathVariable("id") Long id)throws Exception {
        Product  product =  productService.getById(id);
            return JsonResponse.success(product);
    }




    /**
    * 描述：管理员根据Id删除（真实删除）
    *
    */
    @RequestMapping(value = "/super", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteByIdSuper(/*@RequestParam("userid") Long userId,*/@RequestParam("id") Long id) throws Exception {
        //if(productService.identifyUser(userId,productService.getById(id).getShopId())){
        productService.removeById(id);
        return JsonResponse.success("删除成功");
    }

    /**
     * 描述：普通用户根据Id删除（逻辑删除）
     *
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse deleteByIdLogic(/*@RequestParam("userid") Long userId,*/@RequestParam("id") Long id,HttpSession session) throws Exception {
        //if(productService.identifyUser(userId,productService.getById(id).getShopId())){
        //if(productService.identifyUser(userId,productService.getById(id).getShopId())){
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if(productService.identifyUser(currentUser.getId(),productService.getById(id).getShopId())){
            Product product=productService.getById(id);
            //productService.removeById(id);
            product.setStatus(0);
            productService.updateById(product);
            return JsonResponse.success("删除成功");
        }
        return JsonResponse.failure("删除失败");
    }



    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateProduct(@RequestBody Product  product,HttpSession session/*,@RequestParam("userid") Long userId*/) throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        //if(productService.identifyUser(userId,product.getShopId())){
        if(productService.identifyUser(currentUser.getId(),product.getShopId())){
            productService.updateById(product);
            return JsonResponse.success("更新成功");
        }
        return JsonResponse.failure("更新失败");
    }




    /**
     * 描述：普通用户根据product_name 查询所有
     *
     */
    @RequestMapping(value = "/name")
    @ResponseBody
    public JsonResponse getByName(@RequestParam String name)throws Exception {
        List<Product> lists=productMapper.getByName(name);
        return JsonResponse.success(lists);
    }



    /**
     * 描述：管理员根据product_name 查询所有
     *
     */
    @RequestMapping(value = "/super/name")
    @ResponseBody
    public JsonResponse getByNameSuper(@RequestParam String name)throws Exception {
        List<Product> lists=productMapper.getByNameSuper(name);
        return JsonResponse.success(lists);
    }




    /**
    * 描述:创建Product
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody  Product  product) throws Exception {
        productService.save(product);
        return JsonResponse.success(productService.getById(product.getId()));
    }
}

