package com.cybersoft.cozatore.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

/**
 * JwtHelper là class dùng để tạo (generate) và giải mã (decode) JWT token.
 * JWT (JSON Web Token) là một chuẩn mở để truyền thông tin giữa các bên một cách an toàn dưới dạng JSON.
 * Token này thường dùng để xác thực (authentication) và phân quyền (authorization) trong các ứng dụng web.
 */
@Component
public class JwtHelper {
    /**
     * SECRET_KEY là chuỗi bí mật dùng để ký và xác thực token.
     * Để đơn giản cho người mới học, ta hardcode key này trong code.
     */
    private static final String SECRET_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

    /**
     * Hàm generateToken sẽ tạo ra một JWT token từ dữ liệu truyền vào (data).
     * - data sẽ được lưu vào phần subject (sub) của token.
     * - Token sẽ có thời gian sống là 1 ngày (86400000 ms).
     * - Token được ký bằng thuật toán HMAC-SHA và SECRET_KEY.
     * @param data Dữ liệu muốn lưu vào token (thường là username, userId, ...)
     * @return Chuỗi JWT token
     */
    public String generateToken(String data) {
        // Tạo key từ chuỗi bí mật
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        String jwt = Jwts.builder()
                .setSubject(data) // Lưu data vào subject
                .setIssuedAt(new Date()) // Thời điểm phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Hạn token: 1 ngày
                .signWith(key) // Ký token bằng key bí mật
                .compact(); // Tạo chuỗi token
        return jwt;
    }

    /**
     * Hàm decodeToken sẽ giải mã JWT token và lấy ra dữ liệu (subject) đã lưu khi tạo token.
     * @param token Chuỗi JWT token cần giải mã
     * @return Dữ liệu (subject) bên trong token
     */
    public String decodeToken(String token) {
        // Tạo key từ chuỗi bí mật
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // Thiết lập key để xác thực token
                .build()
                .parseClaimsJws(token) // Giải mã token
                .getBody(); // Lấy phần payload
        return claims.getSubject(); // Lấy subject (data) đã lưu
    }
} 