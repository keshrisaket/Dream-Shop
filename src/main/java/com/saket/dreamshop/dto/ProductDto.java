package com.saket.dreamshop.dto;

import com.saket.dreamshop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private String brand;

    private int inventory;


    private Category category;

    private List<ImageDto> images;

}
