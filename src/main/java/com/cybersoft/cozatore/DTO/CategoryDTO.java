package com.cybersoft.cozatore.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    private int id;
    private String categoryName;
}
