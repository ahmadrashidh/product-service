package com.ahmad.product_service.services;

import com.ahmad.product_service.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product addProduct(Product product);

    Product deleteProductById(Long id);

    Product updateProductById(Long id);
}
