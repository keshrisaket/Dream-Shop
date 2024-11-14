package com.saket.dreamshop.service.product;

import com.saket.dreamshop.entity.Product;
import com.saket.dreamshop.request.AddProductRequest;
import com.saket.dreamshop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);

    Product getProductById(String id);

    void deleteProductById(String id);

    Product updateProduct(ProductUpdateRequest product, String id);

    List<Product> getAllProducts();

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);


}
