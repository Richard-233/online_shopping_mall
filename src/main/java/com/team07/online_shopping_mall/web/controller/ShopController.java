package com.team07.online_shopping_mall.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.common.utls.SecurityUtils;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.mapper.ShopMapper;
import com.team07.online_shopping_mall.model.domain.Product;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.model.dto.UserInfoDTO;
import com.team07.online_shopping_mall.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.service.ShopService;
import com.team07.online_shopping_mall.model.domain.Shop;

import javax.servlet.http.HttpSession;
import java.sql.Wrapper;
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
@RequestMapping("/api/shop")
public class ShopController {

    private final Logger logger = LoggerFactory.getLogger( ShopController.class );

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ProductService productService;


    /**
     * 描述：普通人根据Id 查询111
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getById(@PathVariable("id") Long id)throws Exception {
        Shop  shop =  shopService.getById(id);
        if(shop.getOffline().equals(0))
        return ApiRestResponse.success(shop);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }


    /**
     * 描述：管理员根据Id 查询111
     *
     */
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getByIdSuper(@PathVariable("id") Long id)throws Exception {
        Shop  shop =  shopService.getById(id);
        return ApiRestResponse.success(shop);
    }


    /**
     * 描述：所有人根据userId 查询111
     *
     */
    @RequestMapping(value = "/selectByUserId", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getByUserId(HttpSession session)throws Exception {

        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        Long currentUserId =currentUser.getId();
//        SecurityUtils securityUtils = new SecurityUtils();
//        UserInfoDTO userInfo = securityUtils.getUserInfo();
//        Long currentUserId = userInfo.getId();
        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>();
        wrapper.eq("user_id",currentUserId).eq("offline",0);
        List<Shop> shop=  shopService.list(wrapper);
        if(shop.get(0).getOffline().equals(0))
            return ApiRestResponse.success(shop);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }




    /**
     * 描述：普通人根据Name 查询111
     *
     */
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getByName(@RequestParam String name)throws Exception {
        List<Shop> lists=shopMapper.getByName(name);
        if(lists.size()>0)
        return ApiRestResponse.success(lists);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }



    /**
     * 描述：管理员根据Name 查询111
     *
     */
    @RequestMapping(value = "/admin/name", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getByNameSuper(@RequestParam String name)throws Exception {
        List<Shop> lists=shopMapper.getByNameSuper(name);
        if(lists.size()>0)
            return ApiRestResponse.success(lists);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }



    /**
     * 描述：普通人查询所有（按评分从高到低排列）111
     *
     */
    @RequestMapping(value = "/selectall", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getAll()throws Exception {
        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>().eq("offline",0).orderByDesc("score");
        List<Shop> shops=shopService.list(wrapper);
        if(shops.size()>0)
            return ApiRestResponse.success(shops);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }



    /**
     * 描述：管理员查询所有（按评分从高到低排列）111
     *
     */
    @RequestMapping(value = "/admin/selectall", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getAllSuper()throws Exception {
        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>().orderByDesc("score");
        List<Shop> shops=shopService.list(wrapper);
        if(shops.size()>0)
            return ApiRestResponse.success(shops);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }


    /**
     * 描述：普通人根据商铺类别查询（按评分从高到低排列）111
     *
     */
    @RequestMapping(value = "/selectbycatalog", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getByCatalog(@RequestParam Long catalogId)throws Exception {
        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>().eq("offline",0).eq("catalog_id",catalogId).orderByDesc("score");
        List<Shop> shops=shopService.list(wrapper);
        if(shops.size()>0)
            return ApiRestResponse.success(shops);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }



    /**
     * 描述：管理员根据商铺类别查询（按评分从高到低排列）111
     *
     */
    @RequestMapping(value = "/admin/selectbycatalog", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getByCatalogSuper(@RequestParam Long catalogId)throws Exception {
        QueryWrapper<Shop> wrapper=new QueryWrapper<Shop>().eq("catalog_id",catalogId).orderByDesc("score");
        List<Shop> shops=shopService.list(wrapper);
        if(shops.size()>0)
            return ApiRestResponse.success(shops);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }



    /**
     * 描述：普通人根据Id删除（逻辑删除）1111
     *
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse deleteByIdLogic(Long id, HttpSession session) throws Exception {
//        SecurityUtils securityUtils = new SecurityUtils();
//        UserInfoDTO userInfo = securityUtils.getUserInfo();
//        Long currentUserId = userInfo.getId();
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        Long currentUserId =currentUser.getId();
        //System.out.println(id);
        if(shopService.identifyUser(currentUserId,shopService.getById(id).getUserId())||currentUser.getRole().equals(2)){
        //User currentUser = (User) session.getAttribute(Constant.MALL_USER);
//        if(shopService.identifyUser(currentUser.getId(),shopService.getById(id).getUserId())||currentUser.getRole().equals(2)){
            Shop shop=shopService.getById(id);
            shop.setOffline(1);
            shopService.updateById(shop);
            List<Product> products=new ArrayList<>();
            QueryWrapper<Product> wrapper=new QueryWrapper<Product>();
            wrapper.eq("shop_id",id);
            products=productService.list(wrapper);
            for(int i=0;i<products.size();i++){
                products.get(i).setStatus(0);
            }
            //System.out.println(products);
            productService.updateBatchById(products);
            return ApiRestResponse.success("删除成功");
        }
        return ApiRestResponse.error(MallExceptionEnum.DELETE_FAILED);
    }



    /**
     * 描述：管理员根据Id删除（真实删除）1111
     *
     */
    @RequestMapping(value = "/admin/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ApiRestResponse deleteByIdSuper(@RequestParam("id") Long id) throws Exception {
        shopService.removeById(id);
        return ApiRestResponse.success("删除成功");
    }



    /**
     * 描述：根据Id 更新（如果是管理员，那么就允许更新所有字段；如果是普通人，那么只允许更新部分字段）111
     *   对于普通人，前端只提供部分字段的接口就是，后端就调这个接口
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse updateProduct(Shop  shop,HttpSession session) throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if(shopService.identifyUser(currentUser.getId(),shop.getUserId())||currentUser.getRole().equals(2)){
//        SecurityUtils securityUtils = new SecurityUtils();
//        UserInfoDTO userInfo = securityUtils.getUserInfo();
//        Long currentUserId = userInfo.getId();
//        if(shopService.identifyUser(currentUserId,shop.getUserId())||userInfo.getUserType().equals(2L)){
            shopService.updateById(shop);
            return ApiRestResponse.success(shop);
        }
        return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
        //return JsonResponse.failure("wrong");
    }



    /**
     * 描述:所有人创建Shop1111
     *
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse create(Shop  shop,HttpSession session) throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if(currentUser.getId().equals(shop.getUserId())||currentUser.getRole().equals(2)){
//        SecurityUtils securityUtils = new SecurityUtils();
//        UserInfoDTO userInfo = securityUtils.getUserInfo();
//        Long currentUserId = userInfo.getId();
//        System.out.println(99999);
//        System.out.println(shop);
//        if(currentUserId.equals(shop.getUserId())||userInfo.getUserType().equals(2L)){
            shopService.save(shop);
        }
        QueryWrapper<Shop> wrapper= Wrappers.query();
        wrapper.eq("user_id",shop.getUserId()).eq("offline",0);
        if(shopService.count(wrapper)==1){
            return ApiRestResponse.success(shop);
        }
        if(shopService.count(wrapper)==2){
            shopService.removeById(shop.getId());
            return ApiRestResponse.error(MallExceptionEnum.ADD_FAILED);
        }
        return ApiRestResponse.error(MallExceptionEnum.ADD_FAILED);
    }



    /**
     * 描述:管理员创建Shop111
     *
     */
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse create(@RequestBody  Shop  shop) throws Exception {
        shopService.save(shop);
        QueryWrapper<Shop> wrapper= Wrappers.query();
        wrapper.eq("user_id",shop.getUserId()).eq("offline",0);
        if(shopService.count(wrapper)==1){
            return ApiRestResponse.success(shop);
        }
        if(shopService.count(wrapper)==2){
            shopService.removeById(shop.getId());
            return ApiRestResponse.error(MallExceptionEnum.ADD_FAILED);
        }
        return ApiRestResponse.error(MallExceptionEnum.ADD_FAILED);
    }
}

