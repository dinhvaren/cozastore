package com.cybersoft.cozatore.Service.imp;

import com.cybersoft.cozatore.DTO.ProductDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceImp {
    void save( MultipartFile file, String title, double price, int idCategory, String tags);
    List<ProductDTO> getAllProducts();
}
