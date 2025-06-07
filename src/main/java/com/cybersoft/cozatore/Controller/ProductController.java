// Controller quản lý các API liên quan đến sản phẩm
package com.cybersoft.cozatore.Controller;

import com.cybersoft.cozatore.DTO.ProductDTO;
import com.cybersoft.cozatore.Service.imp.FileServiceImp;
import com.cybersoft.cozatore.Service.imp.ProductServiceImp;
import com.cybersoft.cozatore.payload.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductServiceImp productServiceImp;

    // API lấy tất cả sản phẩm (demo trả về chuỗi)
    @GetMapping("")
    public ResponseEntity<?> getAllProduct() {
        List<ProductDTO> list = productServiceImp.getAllProducts();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    // API thêm mới sản phẩm (demo trả về chuỗi)
    @PostMapping("")
    public ResponseEntity<?> insertProduct(@RequestParam MultipartFile file,
                                           @RequestParam String title,
                                           @RequestParam double price,
                                           @RequestParam int idCategory,
                                           @RequestParam(required = false) String tags) {
        logger.info("Insert product " + file.getOriginalFilename()
                + file.getSize() + " " + title + " " + price + " " + idCategory);
        productServiceImp.save(file, title, price, idCategory, tags);
        return new ResponseEntity<>("Insert products", HttpStatus.OK);
    }
}
