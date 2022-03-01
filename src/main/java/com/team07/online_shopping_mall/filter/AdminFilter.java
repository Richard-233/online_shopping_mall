package com.team07.online_shopping_mall.filter;

import com.team07.online_shopping_mall.common.Constant;
import com.team07.online_shopping_mall.model.domain.User;
import com.team07.online_shopping_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 管理员身份校验过滤器
 */
public class AdminFilter implements Filter {
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        servletResponse.setCharacterEncoding("UTF-8");
        if (currentUser == null) {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"msg\": \"用户名未登录\",\n" +
                    "    \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
            return;
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"status\": 10009,\n" +
                    "    \"msg\": \"该账户不是管理员\",\n" +
                    "    \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
        }
    }

    @Override
    public void destroy() {
    }
}
