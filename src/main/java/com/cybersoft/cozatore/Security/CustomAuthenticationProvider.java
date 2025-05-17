package com.cybersoft.cozatore.Security;

import com.cybersoft.cozatore.Service.LoginService;
import com.cybersoft.cozatore.Service.imp.LoginSeviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private LoginSeviceImp loginSeviceImp;
//  nếu trả ra là 1 authentication là thành công ngược lại là thất bại
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials().toString();
        if (loginSeviceImp.CheckLogin(username, password)) {
            return authentication;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO: Update with your specific supported authentication class, e.g. UsernamePasswordAuthenticationToken.class
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
