package com.team07.online_shopping_mall.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 常量值
 */
@Component
public class Constant {
    public static final String MALL_USER = "mall_user";
    public static final String SALT = "7dbajwbds.;'0[3";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price desc", "price asc");
    }
}
