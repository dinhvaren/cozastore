package com.cybersoft.cozatore.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "productdetail")
public class ProductdetailEntity {
    @Id
    @Column(name = "id_product_detail", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color")
    private ColorEntity idColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private ProductEntity idProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_size")
    private SizeEntity idSize;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "description")
    private String description;

}