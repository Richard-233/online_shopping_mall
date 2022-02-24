package com.team07.online_shopping_mall.common.utls;

import org.apache.commons.lang3.StringUtils;

public class MyStringUtils {

    public static boolean isEmptyOrIsNull(String s){
        int strLen = s == null ? 0 : s.length();
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }
}
