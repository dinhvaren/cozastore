package com.cybersoft.cozatore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("")
    public ResponseEntity<?>  login(@RequestParam String username, @RequestParam String password) {

        return new ResponseEntity<>("Hello login page", HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?>  signin(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(token);
        return new ResponseEntity<>("Hello signin page", HttpStatus.OK);
    }
}
