// Cấu hình bảo mật cho ứng dụng Spring Boot
package com.cybersoft.cozatore.Security;

import com.cybersoft.cozatore.Filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Bean mã hóa mật khẩu, sử dụng BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Inject provider xác thực custom
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
//  custom lại AuthenticationManager của Security  để sử dụng CustomProvider mà mình tạo ra
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // Cấu hình AuthenticationManager sử dụng provider custom
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }
    // Inject filter kiểm tra JWT
    @Autowired
    private JwtFilter jwtFilter;
    // thay đổi thông tin về rule đường dẫn của Security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Cấu hình các rule bảo mật, filter, session, ...
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST,"/product").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                // Thêm filter kiểm tra JWT trước filter xác thực mặc định
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults())
                .build();
    }
}
