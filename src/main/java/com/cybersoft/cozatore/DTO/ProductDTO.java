package com.cybersoft.cozatore.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {
    private int id;
    private String images;
    private String title;
    private double price;
}
