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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean CheckLogin(String username, String password) {
        UserEntity userEntity = userRepository.findByEmail(username);
//      nếu truy vấn có dữ liệu tức user tồn tại
//      kiểm tra password trong database có match với password user truyền lên hay không
      if (userEntity != null && passwordEncoder.matches(password, userEntity.getPassword())) {
          return true;
      }
        return false;
    }

}
