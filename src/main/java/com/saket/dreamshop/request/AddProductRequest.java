package com.saket.dreamshop.request;

import com.saket.dreamshop.entity.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest
{

    private String name;
    private String description;
    private BigDecimal price;
    private  String brand;

    private int inventory;

    private Category category;




}
