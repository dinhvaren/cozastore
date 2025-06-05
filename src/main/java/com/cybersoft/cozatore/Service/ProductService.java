package com.cybersoft.cozatore.Service;

import com.cybersoft.cozatore.Entity.CategoryEntity;
import com.cybersoft.cozatore.Entity.ProductEntity;
import com.cybersoft.cozatore.Repository.ProductRepository;
import com.cybersoft.cozatore.Service.imp.FileServiceImp;
import com.cybersoft.cozatore.Service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductService implements ProductServiceImp {
    @Autowired
    private FileServiceImp fileServiceImp;

    @Autowired
    private ProductRepository productRepository;
    @Override
    public void save(MultipartFile file, String title, double price, int idCategory, String tags){
        fileServiceImp.save(file);
        ProductEntity productEntity = new ProductEntity();

        productEntity.setImages(file.getOriginalFilename());
        productEntity.setTitle(title);
        productEntity.setPrice(price);
        productEntity.setTags(tags != null ? tags : "");

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(idCategory);
        productEntity.setCategory(categoryEntity);
        try {
            productRepository.save(productEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error saving product " + e.getMessage());
        }
    }
}
