// Controller quản lý các API liên quan đến sản phẩm
package com.cybersoft.cozatore.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // API lấy tất cả sản phẩm (demo trả về chuỗi)
    @GetMapping("")
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<>("All products", HttpStatus.OK);
    }
    // API thêm mới sản phẩm (demo trả về chuỗi)
    @PostMapping("")
    public ResponseEntity<?> insertProduct(@RequestParam MultipartFile file,
                                           @RequestParam String title,
                                           @RequestParam double price,
                                           @RequestParam int id_category,
                                           @RequestParam(required = false) String tags) {
        logger.info("Insert product " + file.getOriginalFilename()
                + file.getSize() + " " + title + " " + price + " " + id_category);
        return new ResponseEntity<>("Insert products", HttpStatus.OK);
    }
}
