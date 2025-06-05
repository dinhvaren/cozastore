// Package chứa filter kiểm tra JWT cho ứng dụng
package com.cybersoft.cozatore.Filter;

// Import các class cần thiết cho filter
import com.cybersoft.cozatore.jwt.JwtHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Đánh dấu class này là một service để Spring quản lý (có thể dùng @Component hoặc @Service đều được)
@Service
// OncePerRequestFilter: filter này chỉ chạy một lần cho mỗi request
public class JwtFilter extends OncePerRequestFilter {

    // Inject helper để xử lý JWT (tạo, giải mã token)
    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();

    // Hàm này sẽ được gọi mỗi khi có request đi qua filter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy giá trị token mà client truyền trên header (thường là header Authorization)
        String token = request.getHeader("Authorization");
        // Bước 1: lấy token từ giá trị của header, dùng Optional để tránh lỗi null
        Optional<String> tokenOptional = Optional.ofNullable(token);
        // Bước 2: cắt chuỗi bỏ chữ Bearer đi để lấy được token (nếu token tồn tại)
        if (tokenOptional.isPresent()) {
            // Cắt bỏ tiền tố "Bearer " (7 ký tự đầu) để lấy ra chuỗi token thực sự
            String tokenDecoded = tokenOptional.get().substring(7);
            // Bước 3: giải mã token ra (giả sử jwtHelper.decodeToken(...) đã được inject hoặc khởi tạo)
            if (!tokenDecoded.isEmpty()) {
                // Giải mã token để lấy dữ liệu bên trong (thường là username hoặc thông tin user)
                String data = jwtHelper.decodeToken(tokenDecoded);
//              tạo ra custom type để Gson hỗ trợ parse json kiểu list
                Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>() {}.getType();
                List<GrantedAuthority> listRoles = gson.fromJson(data, listType);
                System.out.println("kiểm tra Role " + data + ": " + listRoles.size());
                // Bước 4: nếu giải mã thành công tạo ra Security Context Holder
                if (data != null) {
//                  Tạo ContextHolder để bypass qua các filter của Security
                    // Tạo đối tượng xác thực rỗng (chưa gán quyền), mục đích là đánh dấu request này đã xác thực thành công
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("", "", listRoles);
                    // Lấy context hiện tại của Security
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    // Gán đối tượng xác thực vào context để các filter phía sau biết request này đã xác thực
                    securityContext.setAuthentication(authenticationToken);
                }
            }
        }
        // Tiếp tục chuỗi filter (cho request đi tiếp qua các filter khác hoặc controller)
        filterChain.doFilter(request, response);
    }
}
