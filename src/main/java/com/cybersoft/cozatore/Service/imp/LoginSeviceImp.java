// Interface định nghĩa hợp đồng cho service đăng nhập
package com.cybersoft.cozatore.Service.imp;

import com.cybersoft.cozatore.Entity.UserEntity;

public interface LoginSeviceImp {
    // Hàm kiểm tra đăng nhập, trả về UserEntity nếu thành công
    UserEntity CheckLogin(String username, String password);
}
