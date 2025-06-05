package com.cybersoft.cozatore.Controller;

import com.cybersoft.cozatore.jwt.JwtHelper;
import com.cybersoft.cozatore.payload.response.BaseResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller này dùng để xử lý login và tạo/giải mã JWT token.
 * JWT giúp xác thực người dùng mà không cần lưu session trên server (stateless).
 */
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    // Inject AuthenticationManager để xác thực user
    @Autowired
    private AuthenticationManager authenticationManager;

    // Inject helper xử lý JWT
    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("")
    public ResponseEntity<?> login() {
        // Giải mã token mẫu, lấy ra dữ liệu bên trong (ở đây là subject)
        /**
         * API này dùng để giải mã (decode) một JWT token mẫu.
         * Thường dùng để kiểm tra hoặc demo cách lấy dữ liệu từ token.
         * @return Dữ liệu (subject) bên trong token
         */
        String data = jwtHelper.decodeToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJbe1wicm9sZVwiOlwiUk9MRV9BRE1JTlwifV0iLCJpYXQiOjE3NDkxMTE4MzcsImV4cCI6MTc0OTE5ODIzN30.UkdMicAfCQZt5yevH6k_ytFdG0b8Oz150pcrwIii5nzPRAPlM_d-7ujC8EIL7DhpvPal_xuvzZ2c15xETHZSUQ");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
        logger.info("Request username: " + username);
        // Tạo đối tượng xác thực từ username và password
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // Xác thực thông tin đăng nhập (nếu sai sẽ báo lỗi)
        Authentication authentication = authenticationManager.authenticate(token);
        String json = gson.toJson(authentication.getAuthorities());
        // Tạo JWT token với dữ liệu json
        String jwtToken = jwtHelper.generateToken(json);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(jwtToken);
        // Trả về token cho client
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
