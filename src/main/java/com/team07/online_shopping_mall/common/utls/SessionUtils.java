package com.team07.online_shopping_mall.common.utls;

import com.team07.online_shopping_mall.model.domain.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    private static final String USERKEY = "sessionUser";

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
        // true == allow create
    }

    public static User getCurrentUserInfo() {
        return (User) session().getAttribute(USERKEY);
    }

    public static void saveCurrentUserInfo(User user) {
        session().setAttribute(USERKEY, user);
    }
}
