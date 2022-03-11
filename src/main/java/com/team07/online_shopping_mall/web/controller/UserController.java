package com.team07.online_shopping_mall.web.controller;

import com.team07.online_shopping_mall.common.ApiRestResponse;
import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.common.JsonResponse;
import com.team07.online_shopping_mall.common.utls.SecurityUtils;
import com.team07.online_shopping_mall.exception.MallException;
import com.team07.online_shopping_mall.exception.MallExceptionEnum;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.model.dto.UserInfoDTO;
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
     * @param
     * @return
     * @throws MallException
     */
    @PostMapping("/register")
    @ResponseBody
//    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws MallException {
    public ApiRestResponse register(@RequestBody User user) throws MallException {
        if (StringUtils.isEmpty(user.getUsername())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (user.getPassword().length() < 8) {
            return ApiRestResponse.error(MallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(user.getUsername(), user.getPassword());
        return ApiRestResponse.success();
    }

    /**
     * 登录接口
     *
     * @param
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestBody User user, HttpSession session) throws MallException {
        if (StringUtils.isEmpty(user.getUsername())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user_new = userService.login(user.getUsername(), user.getPassword());
        //保存用户信息除了密码
        user_new.setPassword(null);
        session.setAttribute(Constant.MALL_USER, user_new);
        return ApiRestResponse.success(user_new);
    }

    /**
     *
     *
     * @param userNickname
     * @param userImage
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/updateInfo")
    @ResponseBody
    public ApiRestResponse updataUserInfo(@RequestParam("nickname") String userNickname, @RequestParam("image_url") String userImage, HttpSession session) throws MallException {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setNickname(userNickname);
        user.setImage(userImage);
        userService.updateInfo(user);
        return ApiRestResponse.success();
    }

    /**
     * 登出接口
     *
     * @param session
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
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
    public ApiRestResponse sellerLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws MallException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //校验是否是卖家
        if (userService.checkRole(user)) {
            //保存用户信息除了密码
            user.setPassword(null);
            session.setAttribute(Constant.MALL_USER, user);
            return ApiRestResponse.success(user);
        } else {
            return ApiRestResponse.error(MallExceptionEnum.NEED_OPEN_SHOP);
        }
    }

    /**
     * 管理员登录接口
     *
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws MallException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //校验是否是卖家
        if (userService.checkAdminRole(user)) {
            //保存用户信息除了密码
            user.setPassword(null);
            session.setAttribute(Constant.MALL_USER, user);
            return ApiRestResponse.success(user);
        } else {
            return ApiRestResponse.error(MallExceptionEnum.NEED_ADMIN);
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
    public ApiRestResponse updateUserRole(HttpSession session) throws MallException {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setNickname(currentUser.getNickname());
        user.setImage(currentUser.getImage());
        user.setRole(1);
        userService.updateInfo(user);
        return ApiRestResponse.success();
    }


    /**
     * 描述：根据Id 查询
     */
    @GetMapping("/loginUserInfo")
    @ResponseBody
    public ApiRestResponse getById(HttpSession session) throws Exception {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEnum.NEED_LOGIN);
        }
//        Long id = currentUser.getId();
//        User user = userService.getById(id);
        return ApiRestResponse.success(currentUser);
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



    /**
     * 描述:登录前端User   //19:11
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse<UserInfoDTO> getUserInfo() throws Exception {
        return JsonResponse.success(SecurityUtils.getUserInfo());
    }


}

