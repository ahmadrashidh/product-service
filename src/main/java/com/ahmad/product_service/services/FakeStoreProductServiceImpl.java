package com.ahmad.product_service.services;

import com.ahmad.product_service.dtos.FakeStoreProductDto;
import com.ahmad.product_service.models.Category;
import com.ahmad.product_service.models.Product;
import com.ahmad.product_service.thirdpartyclients.FakeStoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FakeStoreProductServiceImpl implements ProductService {

    private FakeStoreClient fakeStoreClient;

    public FakeStoreProductServiceImpl(FakeStoreClient fakeStoreClient){
        this.fakeStoreClient = fakeStoreClient;
    }



    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductList = this.fakeStoreClient.getAllProducts();
        List<Product> productList = new ArrayList<>();
        for(FakeStoreProductDto productDto : fakeStoreProductList){
            productList.add(getProductFromFakeStoreProductDto(productDto));
        }

        return productList;
    }

    @Override
    public Product getProductById(Long id) {
        return getProductFromFakeStoreProductDto(this.fakeStoreClient.getProductById(id));
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product deleteProductById(Long id) {
        return null;
    }

    @Override
    public Product updateProductById(Long id) {
        return null;
    }


    private Product getProductFromFakeStoreProductDto(FakeStoreProductDto fakeStoreProduct) {
        Product product = new Product();
        product.setId(fakeStoreProduct.getId());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setName(fakeStoreProduct.getTitle());
        product.setPrice(fakeStoreProduct.getPrice());
        Category category = new Category();
        category.setId(new Random().nextLong(10000));
        category.setName(fakeStoreProduct.getCategory());
        product.setCategory(category);
        return product;
    }
}
