package com.saket.dreamshop.request;

import com.saket.dreamshop.entity.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest
{

    private String id ;
    private String name;
    private String description;
    private BigDecimal price;
    private  String brand;

    private int inventory;

    private Category category;
}
