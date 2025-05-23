package com.ahmad.product_service.controllers;

import com.ahmad.product_service.dtos.CategoryDto;
import com.ahmad.product_service.dtos.ProductDto;
import com.ahmad.product_service.dtos.SendEmailEventDto;
import com.ahmad.product_service.models.Category;
import com.ahmad.product_service.models.Product;
import com.ahmad.product_service.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ProductController(ProductService productService, KafkaTemplate<String,String> kafkaTemplate, ObjectMapper objectMapper){
        this.kafkaTemplate = kafkaTemplate;
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public  Page<ProductDto> getAllProducts(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
        Page<Product> productPage = this.productService.getAllProducts(pageNo, pageSize);

        return this.convertToDtoPage(productPage);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") Long id) throws JsonProcessingException {
        SendEmailEventDto sendEmailEventDto = new SendEmailEventDto();
        sendEmailEventDto.setTo("ahmadrashidh.ameerhamsha@gmail.com");
        sendEmailEventDto.setFrom("jivefog@gmail.com");
        sendEmailEventDto.setSubject("Product Details");
        sendEmailEventDto.setBody("Product Ordered");

        kafkaTemplate.send("send-email",objectMapper.writeValueAsString(sendEmailEventDto));
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
        productDto.setImage(product.getImage());
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
        product.setImage(productDto.getImage());
        return product;
    }

    public Page<ProductDto> convertToDtoPage(Page<Product> productPage) {
        return productPage.map(this::getProductDtoFromProduct);
    }
}
