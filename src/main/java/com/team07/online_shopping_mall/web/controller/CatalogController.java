package com.team07.online_shopping_mall.web.controller;

import com.github.pagehelper.PageInfo;
import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.model.request.AddCategoryReq;
import com.team07.online_shopping_mall.model.request.UpdateCategoryReq;
import com.team07.online_shopping_mall.model.vo.CatalogVO;
import com.team07.online_shopping_mall.service.UserService;
import org.springframework.beans.BeanUtils;
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
import javax.validation.Valid;
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
@RequestMapping("/api/catalog")
public class CatalogController {

    private final Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    private CatalogService catalogService;

    @Autowired
    UserService userService;

    /**
     * 创建目录分类
     *
     * @param session
     * @param addCategoryReq
     * @return
     */
    @PostMapping("/admin/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean AdminRole = userService.checkAdminRole(currentUser);
        if (AdminRole) {
            //是管理员执行操作
            catalogService.add(addCategoryReq);
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
        }
    }

    /**
     * 更新目录
     *
     * @param session
     * @param updateCategoryReq
     * @return
     */
    @PostMapping("/admin/update")
    @ResponseBody
    public ApiRestResponse updateCategory(HttpSession session, @Valid @RequestBody UpdateCategoryReq updateCategoryReq) {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean AdminRole = userService.checkAdminRole(currentUser);
        if (AdminRole) {
            //是管理员执行操作
            Catalog catalog = new Catalog();
            BeanUtils.copyProperties(updateCategoryReq, catalog);
            catalogService.update(catalog);
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
        }
    }

    /**
     * 删除目录分类
     *
     * @return
     */
    @PostMapping("/admin/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Long id) {
        catalogService.delete(id);
        return ApiRestResponse.success();
    }

    @PostMapping("/admin/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = catalogService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/custom/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustom() {
        List<CatalogVO> catalogVOS = catalogService.listCategoryForCustom();
        return ApiRestResponse.success(catalogVOS);
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

