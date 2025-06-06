package com.cybersoft.cozatore.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_blog")
    private BlogEntity idBlog;

}