// Controller xử lý các request liên quan đến danh mục sản phẩm
package com.cybersoft.cozatore.Controller;

import com.cybersoft.cozatore.Service.CategoryService;
import com.cybersoft.cozatore.Service.imp.CategoryServiceImp;
import com.cybersoft.cozatore.payload.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    // Tiêm CategoryServiceImp để xử lý logic nghiệp vụ
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    // API endpoint để lấy danh sách tất cả các danh mục
    @GetMapping("")
    public ResponseEntity<?> getCategory() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(categoryServiceImp.getAllCategory());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }
}
