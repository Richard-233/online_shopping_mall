package com.team07.online_shopping_mall.web.controller;

import com.team07.online_shopping_mall.common.ApiRestReasponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.exception.GlobalExceptionHandler;
import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * 前端控制器
 *
 * @author team07
 * @version v1.0
 * @since 2022-02-24
 */
@Controller
@RequestMapping("/api/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 注册接口
     *
     * @param userName
     * @param password
     * @return
     * @throws MallException
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestReasponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws MallException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (password.length() < 8) {
            return ApiRestReasponse.error(MallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName, password);
        return ApiRestReasponse.success();
    }

    /**
     * 登录接口
     *
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiRestReasponse login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws MallException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //保存用户信息除了密码
        user.setPassword(null);
        session.setAttribute(Constant.MALL_USER, user);
        return ApiRestReasponse.success(user);
    }

    /**
     * 更新用户信息接口
     *
     * @param userNickname
     * @param userImage
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/updateInfo")
    @ResponseBody
    public ApiRestReasponse updataUserInfo(@RequestParam("nickname") String userNickname, @RequestParam("image_url") String userImage, HttpSession session) throws MallException {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setNickname(userNickname);
        user.setImage(userImage);
        userService.updateInfo(user);
        return ApiRestReasponse.success();
    }

    /**
     * 登出接口
     *
     * @param session
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public ApiRestReasponse logout(HttpSession session) {
        session.removeAttribute(Constant.MALL_USER);
        return ApiRestReasponse.success();
    }

    /**
     * 卖家登录接口
     *
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/sellerLogin")
    @ResponseBody
    public ApiRestReasponse sellerLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws MallException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //校验是否是卖家
        if (userService.checkRole(user)) {
            //保存用户信息除了密码
            user.setPassword(null);
            session.setAttribute(Constant.MALL_USER, user);
            return ApiRestReasponse.success(user);
        } else {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_OPEN_SHOP);
        }
    }

    /**
     * 买家升级为卖家
     *
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/updateUserRole")
    @ResponseBody
    public ApiRestReasponse updateUserRole(HttpSession session) throws MallException {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestReasponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setNickname(currentUser.getNickname());
        user.setImage(currentUser.getImage());
        user.setRole(1);
        userService.updateInfo(user);
        return ApiRestReasponse.success();
    }


    /**
     * 描述：根据Id 查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse getById(@PathVariable("id") Long id) throws Exception {
        User user = userService.getById(id);
        return JsonResponse.success(user);
    }

    /**
     * 描述：根据Id删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResponse deleteById(@PathVariable("id") Long id) throws Exception {
        userService.removeById(id);
        return JsonResponse.success(null);
    }


    /**
     * 描述：根据Id 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public JsonResponse updateUser(User user) throws Exception {
        userService.updateById(user);
        return JsonResponse.success(null);
    }


    /**
     * 描述:创建User
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse create(User user) throws Exception {
        userService.save(user);
        return JsonResponse.success(null);
    }
}

