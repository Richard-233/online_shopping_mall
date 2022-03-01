package com.team07.online_shopping_mall.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * 常量值
 */
public class Constant {
    public static final String MALL_USER = "mall_user";
    public static final String SALT = "7dbajwbds.;'0[3";

    @Value("${file.upload.dir}")
    public static String FILE_UPLOAD_DIR;
}
