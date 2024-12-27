package com.ahmad.product_service.controllers;

import com.ahmad.product_service.dtos.CategoryDto;
import com.ahmad.product_service.dtos.ProductDto;
import com.ahmad.product_service.models.Category;
import com.ahmad.product_service.models.Product;
import com.ahmad.product_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        List<Product> productList = this.productService.getAllProducts();
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product: productList){
            productDtoList.add(getProductDtoFromProduct(product));
        }

        return productDtoList;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") Long id){
        return getProductDtoFromProduct(this.productService.getProductById(id));
    }


    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        Product product = this.productService.addProduct(getProductFromProductDto(productDto));
        return getProductDtoFromProduct(product);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable("id") Long id,@RequestBody ProductDto product){
        return getProductDtoFromProduct(this.productService.updateProductById(id, getProductFromProductDto(product)));
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProductById(@PathVariable("id") Long id){

        return getProductDtoFromProduct(this.productService.deleteProductById(id));
    }

    private ProductDto getProductDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setName(product.getCategory().getName());
        productDto.setCategory(categoryDto);
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    private Product getProductFromProductDto(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        Category category = new Category();
        category.setId(productDto.getCategory().getId());
        category.setName(productDto.getCategory().getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        return product;
    }
}
