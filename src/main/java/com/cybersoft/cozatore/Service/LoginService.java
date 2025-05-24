// Service xử lý logic đăng nhập cho ứng dụng
package com.cybersoft.cozatore.Service;

import com.cybersoft.cozatore.Entity.UserEntity;
import com.cybersoft.cozatore.Repository.UserRepository;
import com.cybersoft.cozatore.Service.imp.LoginSeviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginSeviceImp {
    // Inject repository thao tác với bảng user
    @Autowired
    private UserRepository userRepository;

    // Inject bean mã hóa mật khẩu
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity CheckLogin(String username, String password) {
        // Tìm user theo email (username)
        UserEntity userEntity = userRepository.findByEmail(username);
//      nếu truy vấn có dữ liệu tức user tồn tại
//      kiểm tra password trong database có match với password user truyền lên hay không
      if (userEntity != null && passwordEncoder.matches(password, userEntity.getPassword())) {
          return userEntity;
      }
        return null;
    }

}
