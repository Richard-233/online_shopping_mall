package com.team07.online_shopping_mall.web.controller;

import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.mapper.ProductMapper;
import com.team07.online_shopping_mall.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.service.ProductService;
import com.team07.online_shopping_mall.model.domain.Product;
import org.springframework.web.multipart.MultipartFile;

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
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@RequestParam("userid") Long userId, @RequestParam("id") Long id) throws Exception {
        if(productService.identifyUser(userId,productService.getById(id).getShopId())){
            productService.removeById(id);
            return JsonResponse.success("删除成功");
        }
        return JsonResponse.failure("删除失败");
    }


    /**
     * 描述：根据Id 更新
     *
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateProduct(@RequestBody Product  product,@RequestParam("userid") Long userId) throws Exception {
        if(productService.identifyUser(userId,product.getShopId())){
            productService.updateById(product);
            return JsonResponse.success("更新成功");
        }
        return JsonResponse.failure("更新失败");
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
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(@RequestBody Product product) throws Exception {
        productService.save(product);
        return JsonResponse.success(productService.getById(product.getId()));
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

