// Entity ánh xạ bảng role trong database
package com.cybersoft.cozatore.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    // Một role có thể có nhiều user (1-n)
    @OneToMany(mappedBy = "role") // lưu ý: tên thuộc tính của entity bên UserEntity
    private List<UserEntity> users;
}
