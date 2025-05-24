package com.cybersoft.cozatore.Controller;

import com.cybersoft.cozatore.jwt.JwtHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller này dùng để xử lý login và tạo/giải mã JWT token.
 * JWT giúp xác thực người dùng mà không cần lưu session trên server (stateless).
 */
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
    /**
     * API này dùng để giải mã (decode) một JWT token mẫu.
     * Thường dùng để kiểm tra hoặc demo cách lấy dữ liệu từ token.
     * @return Dữ liệu (subject) bên trong token
     */
    @PostMapping("")
    public ResponseEntity<?> login() {
        // Giải mã token mẫu, lấy ra dữ liệu bên trong (ở đây là subject)
        String data = jwtHelper.decodeToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoZWxsbyBqd3QiLCJpYXQiOjE3NDc2NzMwNDksImV4cCI6MTc0Nzc1OTQ0OX0.nKKHy66LPU0OnAvzrHokWwY0RyodbA5V3MHomIsQhsT3XvKPoX9fgiv2v89TySXibfJZZ932MK0ESGJLoxr88g");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
        // Tạo đối tượng xác thực từ username và password
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // Xác thực thông tin đăng nhập (nếu sai sẽ báo lỗi)
        Authentication authentication = authenticationManager.authenticate(token);
        String json = gson.toJson(authentication.getAuthorities());
        // Tạo JWT token với dữ liệu json
        String jwtToken = jwtHelper.generateToken(json);
        // Trả về token cho client
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }
}
