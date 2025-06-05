// Repository thao tác với bảng users trong database
package com.cybersoft.cozatore.Repository;

import com.cybersoft.cozatore.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // Tìm user theo email (username)
    UserEntity findByEmail(String email);
}
