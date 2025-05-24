// Entity ánh xạ bảng users trong database
package com.cybersoft.cozatore.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

//    thằng nào là chữ N, khóa ngoại luôn luôn là @ManyToOne, @JoinColum
//    Nếu chữ cuối là One thì là một đối tượng của Entity tham chiếu tới
//    Nếu chữ cuối là Many thì là một list đối tượng của Entity tham chiếu tới
    // Một user chỉ thuộc về một role (n-1)
    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;
}
