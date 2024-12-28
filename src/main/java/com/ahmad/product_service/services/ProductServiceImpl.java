package com.ahmad.product_service.services;

import com.ahmad.product_service.exceptions.ProductNotFoundException;
import com.ahmad.product_service.models.Category;
import com.ahmad.product_service.models.Product;
import com.ahmad.product_service.repositories.CategoryRepository;
import com.ahmad.product_service.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ProductService")
@Primary
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepo;
    private CategoryRepository categoryRepo;

    public ProductServiceImpl(ProductRepository productRepo, CategoryRepository categoryRepo){
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {

        Optional<Product> productOpt = this.productRepo.findById(id);
        if(productOpt.isEmpty())
            throw new ProductNotFoundException("Product with id:" + id + " not found");

        return productOpt.get();

    }

    @Override
    public Product addProduct(Product product) {

        Optional<Category> categoryOpt = this.categoryRepo.findByName(product.getCategory().getName());
        if(categoryOpt.isPresent()){
            product.setCategory(categoryOpt.get());
        } else {
            Category category = this.categoryRepo.save(product.getCategory());
            product.setCategory(category);
        }

        return this.productRepo.save(product);
    }

    @Override
    public Product deleteProductById(Long id) {
        Optional<Product> productOpt = this.productRepo.findById(id);
        if(productOpt.isEmpty())
            throw new ProductNotFoundException("Product with id:" + id + " not found");

        this.productRepo.delete(productOpt.get())  ;

        return productOpt.get();
    }

    @Override
    public Product updateProductById(Long id, Product product) {
        Optional<Product> productOpt = this.productRepo.findById(id);
        if(productOpt.isEmpty())
            throw new ProductNotFoundException("Product with id:" + id + " not found");

        Product existingProduct = productOpt.get();
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImage(product.getImage());
        Optional<Category> categoryOpt = this.categoryRepo.findByName(product.getCategory().getName());
        if(categoryOpt.isPresent()){
            product.setCategory(categoryOpt.get());
        } else {
            Category category = this.categoryRepo.save(product.getCategory());
            existingProduct.setCategory(category);
        }

        return this.productRepo.save(existingProduct);
    }
}
