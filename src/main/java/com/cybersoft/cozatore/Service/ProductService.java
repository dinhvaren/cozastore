// Service xử lý logic nghiệp vụ liên quan đến sản phẩm
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
    // Tiêm FileService để xử lý việc lưu trữ file
    @Autowired
    private FileServiceImp fileServiceImp;

    // Tiêm ProductRepository để tương tác với database
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(MultipartFile file, String title, double price, int idCategory, String tags){
        // Lưu file ảnh sản phẩm
        fileServiceImp.save(file);
        
        // Tạo đối tượng ProductEntity mới
        ProductEntity productEntity = new ProductEntity();

        // Thiết lập thông tin sản phẩm
        productEntity.setImages(file.getOriginalFilename());
        productEntity.setTitle(title);
        productEntity.setPrice(price);
        productEntity.setTags(tags != null ? tags : "");

        // Thiết lập danh mục cho sản phẩm
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(idCategory);
        productEntity.setCategory(categoryEntity);

        try {
            // Lưu sản phẩm vào database
            productRepository.save(productEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error saving product " + e.getMessage());
        }
    }
}
