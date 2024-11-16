package com.saket.dreamshop.controller;


import com.saket.dreamshop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController
{

    private final ProductService productService;


    ProductController(ProductService productService){
        this.productService = productService;
    }









}
