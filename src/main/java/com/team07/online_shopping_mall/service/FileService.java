package com.team07.online_shopping_mall.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileService {

    Map upload(MultipartFile file) throws IOException;

}
