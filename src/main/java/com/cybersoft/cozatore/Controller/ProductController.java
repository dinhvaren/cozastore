// Controller quản lý các API liên quan đến sản phẩm
package com.cybersoft.cozatore.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    // API lấy tất cả sản phẩm (demo trả về chuỗi)
    @GetMapping("")
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<>("All products", HttpStatus.OK);
    }
    // API thêm mới sản phẩm (demo trả về chuỗi)
    @PostMapping("")
    public ResponseEntity<?> insertProduct() {
        return new ResponseEntity<>("All products", HttpStatus.OK);
    }
}
