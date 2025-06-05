package com.cybersoft.cozatore.Service.imp;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ProductServiceImp {
    void save( MultipartFile file, String title, double price, int idCategory, String tags);
}
