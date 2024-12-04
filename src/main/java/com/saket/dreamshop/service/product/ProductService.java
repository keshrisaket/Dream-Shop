package com.saket.dreamshop.service.product;

import com.saket.dreamshop.dto.ImageDto;
import com.saket.dreamshop.dto.ProductDto;
import com.saket.dreamshop.entity.Category;
import com.saket.dreamshop.entity.Image;
import com.saket.dreamshop.entity.Product;
import com.saket.dreamshop.exception.ProductNotFoundException;
import com.saket.dreamshop.repository.CategoryRepository;
import com.saket.dreamshop.repository.ImageRepository;
import com.saket.dreamshop.repository.ProductRepository;
import com.saket.dreamshop.request.AddProductRequest;
import com.saket.dreamshop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;


    @Override
    public Product addProduct(AddProductRequest product) {

        // check if the category is found in the db
        // if yes , set it as the new product category
        // if no , then save it as a new category
        // then set as the new product category.


        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(product.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);
        return productRepository.save(createProduct(product, category));

    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not Found! "));
    }

    @Override
    public void deleteProductById(String id) {

        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ProductNotFoundException("Product not Found!");
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest product, String id) {

        return productRepository.findById(id).
                map(existingProduct -> updateExistingProduct(existingProduct, product))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not Found!"));


    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }


    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;

    }


}
