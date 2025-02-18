package com.ahmad.product_service.services;

import com.ahmad.product_service.exceptions.ProductNotFoundException;
import com.ahmad.product_service.models.Category;
import com.ahmad.product_service.models.Product;
import com.ahmad.product_service.repositories.CategoryRepository;
import com.ahmad.product_service.repositories.ProductRepository;
import com.ahmad.product_service.security.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("ProductService")
//@Primary
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepo;
    private CategoryRepository categoryRepo;

    @Autowired
    private RestTemplate restTemplate;

    public ProductServiceImpl(ProductRepository productRepo, CategoryRepository categoryRepo){
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepo.findAll();
    }

    @Override
    public Page<Product> getAllProducts(int pageNo, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "price");
        Pageable pageable = PageRequest.of(pageNo, page, sort);
        return productRepo.findAll(pageable);
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
