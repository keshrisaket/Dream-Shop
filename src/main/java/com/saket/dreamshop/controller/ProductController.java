package com.saket.dreamshop.controller;


import com.saket.dreamshop.dto.ProductDto;
import com.saket.dreamshop.entity.Product;
import com.saket.dreamshop.exception.ProductNotFoundException;
import com.saket.dreamshop.request.AddProductRequest;
import com.saket.dreamshop.request.ProductUpdateRequest;
import com.saket.dreamshop.response.ApiResponse;
import com.saket.dreamshop.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final ProductService productService;


    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> productList = productService.getAllProducts();
            List<ProductDto> productDtos = productService.getConvertedProducts(productList);
            return ResponseEntity.ok(new ApiResponse("Found!", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable String id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto =  productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Found!", productDto));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{name}/product")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> productsList = productService.getProductsByName(name);
            List<ProductDto> productDto = productService.getConvertedProducts(productsList);
            if (productDto.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found", NOT_FOUND));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            if (product != null) {
                Product newProduct = productService.addProduct(product);
                return ResponseEntity.ok(new ApiResponse("Added!", newProduct));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest productRequest, @PathVariable String id) {
        try {
            Product updatedProduct = productService.updateProduct(productRequest,id);
            return ResponseEntity.ok(new ApiResponse("Updated!", updatedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }


    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted!", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<Product> listProduct = productService.getProductsByBrandAndName(brand, name);
            List<ProductDto> productDto = productService.getConvertedProducts(listProduct);
            if (productDto != null) {
                return ResponseEntity.ok(new ApiResponse("Found!", productDto));
            } else {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }


    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> listProduct = productService.getProductsByCategoryAndBrand(category, brand);
            List<ProductDto> productDtos = productService.getConvertedProducts(listProduct);
            if (productDtos == null) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }



    @GetMapping("/product/by/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDto> productDto = productService.getConvertedProducts(products);
            if (productDto.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }

    }

    @GetMapping("/product/by/category")
    public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            List<ProductDto> productDto = productService.getConvertedProducts(products);
            if (productDto.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", productDto));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/product/count/by/brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            Long val = productService.countProductsByBrandAndName(brand, name);
            if (val == null) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!", val));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }







}
