package com.team07.online_shopping_mall.web.controller;

import com.github.pagehelper.PageInfo;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.mapper.ProductMapper;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.model.query.ProductListQuery;
import com.team07.online_shopping_mall.model.request.ProductListReq;
import com.team07.online_shopping_mall.service.ShopService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.service.ProductService;
import com.team07.online_shopping_mall.model.domain.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;


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
    private ShopService shopService;


    /**
    * 描述：普通用户根据Id 查询111
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getById(@PathVariable("id") Long id)throws Exception {
        Product product = productService.getById(id);
        if (product.getStatus() != 0) {
            product.setStock(null);
            product.setCreateTime(null);
            product.setUpdateTime(null);
            return ApiRestResponse.success(product);
        } else
            return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }

    /**
     * 描述：管理员根据Id 查询111
     *
     */
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiRestResponse getByIdSuper(@PathVariable("id") Long id)throws Exception {
        Product  product =  productService.getById(id);
            return ApiRestResponse.success(product);
    }



    /**
     * 描述：普通用户根据product_name 查询所有,但是下架的无法查询111
     *
     */
    @RequestMapping(value = "/name")
    @ResponseBody
    public ApiRestResponse getByName(@RequestParam String name)throws Exception {
        List<Product> lists=productMapper.getByName(name);
        if(lists.size()>0)
            return ApiRestResponse.success(lists);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }



    /**
     * 描述：管理员根据product_name 查询所有111
     *
     */
    @RequestMapping(value = "/admin/name")
    @ResponseBody
    public ApiRestResponse getByNameSuper(@RequestParam String name)throws Exception {
        List<Product> lists=productMapper.getByNameSuper(name);
        if(lists.size()>0)
            return ApiRestResponse.success(lists);
        else return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
    }



    /**
     * 描述：更新（如果是管理员，那么就允许更新所有字段；如果是普通人，那么只允许更新部分字段，且只允许更新自己商铺里的商品）111
     *  对于普通人，前端只提供部分字段的接口就是，后端就调这个接口
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public ApiRestResponse updateProduct(@RequestBody Product  product,HttpSession session/*,@RequestParam("userid") Long userId*/) throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        //if(productService.identifyUser(userId,product.getShopId())){
        if((productService.identifyUser(currentUser.getId(),productService.getById(product.getId()).getShopId())
                &&productService.getById(product.getId()).getShopId().equals(product.getShopId()))
                ||currentUser.getRole().equals(2)){
            productService.updateById(product);
            return ApiRestResponse.success(product);
        }
        return ApiRestResponse.error(MallExceptionEnum.UPDATE_FAILED);
    }


    /**
     * 描述：普通人根据Id删除（逻辑删除）111
     *
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    @ResponseBody
    public ApiRestResponse deleteByIdLogic(/*@RequestParam("userid") Long userId,*/@RequestParam("id") Long id,HttpSession session) throws Exception {
        //if(productService.identifyUser(userId,productService.getById(id).getShopId())){
        //if(productService.identifyUser(userId,productService.getById(id).getShopId())){
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if(productService.identifyUser(currentUser.getId(),productService.getById(id).getShopId())||currentUser.getRole().equals(2)){
            Product product=productService.getById(id);
            //productService.removeById(id);
            product.setStatus(0);
            productService.updateById(product);
            return ApiRestResponse.success("删除成功");
        }
        return ApiRestResponse.error(MallExceptionEnum.DELETE_FAILED);
    }



    /**
    * 描述：管理员根据Id删除（真实删除）111
    *
    */
    @RequestMapping(value = "/admin/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ApiRestResponse deleteByIdSuper(/*@RequestParam("userid") Long userId,*/@RequestParam("id") Long id) throws Exception {
        //if(productService.identifyUser(userId,productService.getById(id).getShopId())){
        productService.removeById(id);
        return ApiRestResponse.success("删除成功");
    }



    /**
     * 描述:普通人创建Product111
     *
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse create(@RequestBody  Product  product,HttpSession session) throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if(currentUser.getId().equals(shopService.getById(product.getShopId()).getUserId())||currentUser.getRole().equals(2)){
            productService.save(product);
            return ApiRestResponse.success(productService.getById(product.getId()));
        }
        else return ApiRestResponse.error(MallExceptionEnum.ADD_FAILED);
    }


    /**
    * 描述:管理员创建Product1111
    *
    */
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ResponseBody
    public ApiRestResponse create(@RequestBody  Product  product) throws Exception {
        productService.save(product);
        return ApiRestResponse.success(productService.getById(product.getId()));
    }


    /**
     * 描述：店主或者管理员根据Id 推荐/取消推荐111
     *
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.PUT)
    @ResponseBody
    public ApiRestResponse recommendProduct(@RequestParam Long id ,HttpSession session/*,@RequestParam("userid") Long userId*/) throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        Product product=productService.getById(id);
        if(currentUser.getRole()==2||productService.identifyUser(currentUser.getId(),product.getShopId()))
        {
            if(product.getStatus().equals(2)){
                product.setStatus(1);
                productService.updateById(product);
                return ApiRestResponse.success("成功取消推荐");
            }
            if (product.getStatus().equals(1)) {
                product.setStatus(2);
                productService.updateById(product);
                return ApiRestResponse.success("成功推荐");
            }
            if (product.getStatus().equals(0)) {
                return ApiRestResponse.error(MallExceptionEnum.SELECT_FAILED);
            }
        }
        return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
    }

    @PostMapping("/batchUpdateSellStatus")
    @ResponseBody
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids, @RequestParam Integer sellStatus) {
        productService.batchUpdateSellStatus(ids, sellStatus);
        return ApiRestResponse.success();
    }

    @PostMapping("/admin/product/list")
    @ResponseBody
    public ApiRestResponse AdminList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = productService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/seller/product/list")
    @ResponseBody
    public ApiRestResponse sellerList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, HttpSession session) {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        Long currentUserId = currentUser.getId();
        PageInfo pageInfo = productService.listForSeller(pageNum, pageSize, currentUserId);
        return ApiRestResponse.success(pageInfo);
    }

    @GetMapping("/list")
    @ResponseBody
    public ApiRestResponse list(ProductListReq productListReq) {
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }


    @PostMapping("/upload/file")
    @ResponseBody
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file")
            MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFilename = uuid.toString() + suffixName;
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFilename);
        if (!fileDirectory.exists()) {
            if (!fileDirectory.mkdir()) {
                throw new MallException(MallExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/images/" + newFilename);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(MallExceptionEnum.UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }
}

