package com.team07.online_shopping_mall.web.controller;

import com.team07.online_shopping_mall.common.ApiRestReasponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.model.request.AddCategoryReq;
import com.team07.online_shopping_mall.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.service.CatalogService;
import com.team07.online_shopping_mall.model.domain.Catalog;

import javax.servlet.http.HttpSession;


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
@RequestMapping("/api/catalog")
public class CatalogController {

    private final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    private CatalogService catalogService;

    @Autowired
    UserService userService;

    @PostMapping("/add")
    @ResponseBody
    public ApiRestReasponse addCategory(HttpSession session, @RequestBody AddCategoryReq addCategoryReq) {

        if (addCategoryReq.getName() == null || addCategoryReq.getType() == null || addCategoryReq.getParentId() == null || addCategoryReq.getOrderNum() == null) {
            return ApiRestReasponse.error(MallExceptionEnum.PARA_NOT_NULL);
        }
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是卖家
        boolean sellerRole = userService.checkRole(currentUser);
        if (sellerRole) {
            //是卖家执行操作
            catalogService.add(addCategoryReq);
            return ApiRestReasponse.success();
        } else {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_OPEN_SHOP);
        }
    }

    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        Catalog catalog = catalogService.getById(id);
        return JsonResponse.success(catalog);
    }

    /**
    * 描述：根据Id删除
    *
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        catalogService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
    * 描述：根据Id 更新
    *
    */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateCatalog(Catalog  catalog) throws Exception {
        catalogService.updateById(catalog);
        return JsonResponse.success(null);
    }


    /**
    * 描述:创建Catalog
    *
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(Catalog  catalog) throws Exception {
        catalogService.save(catalog);
        return JsonResponse.success(null);
    }
}

