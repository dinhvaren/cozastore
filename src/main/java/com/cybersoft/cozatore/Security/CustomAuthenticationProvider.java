// Provider xác thực custom cho Spring Security, dùng để kiểm tra đăng nhập với logic riêng
package com.cybersoft.cozatore.Security;

import com.cybersoft.cozatore.Entity.UserEntity;
import com.cybersoft.cozatore.Service.LoginService;
import com.cybersoft.cozatore.Service.imp.LoginSeviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    // Inject service kiểm tra đăng nhập
    @Autowired
    private LoginSeviceImp loginSeviceImp;
//  nếu trả ra là 1 authentication là thành công ngược lại là thất bại
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Lấy username và password từ đối tượng authentication
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials().toString();
        // Kiểm tra đăng nhập, trả về user nếu đúng
        UserEntity userEntity = loginSeviceImp.CheckLogin(username, password);
        if (userEntity!= null) {
//          tạo một list nhận vào danh sách quyền theo chuẩn của security
            List<GrantedAuthority> listRole = new ArrayList<GrantedAuthority>();
//          tạo ra một quyền và gán tên quyền truy vấn được từ database để add vào list role ở trên
            SimpleGrantedAuthority role = new SimpleGrantedAuthority(userEntity.getRole().getName());
            listRole.add(role);
//
            // Tạo đối tượng xác thực có quyền (role) lấy từ DB
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("", "", listRole);
            return authenticationToken;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Xác định loại token mà provider này hỗ trợ (ở đây là UsernamePasswordAuthenticationToken)
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
