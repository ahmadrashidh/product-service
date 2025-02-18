package com.ahmad.product_service.services;

import com.ahmad.product_service.dtos.FakeStoreProductDto;
import com.ahmad.product_service.models.Category;
import com.ahmad.product_service.models.Product;
import com.ahmad.product_service.thirdpartyclients.FakeStoreClient;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service("FakeStoreProductService")
@Primary
public class FakeStoreProductServiceImpl implements ProductService {

    private FakeStoreClient fakeStoreClient;
    private RedisTemplate<Long, Object> redisTemplate;

    public FakeStoreProductServiceImpl(FakeStoreClient fakeStoreClient, RedisTemplate<Long, Object> redisTemplate){
        this.fakeStoreClient = fakeStoreClient;
        this.redisTemplate = redisTemplate;
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
    public Page<Product> getAllProducts(int pageNo, int page) {
        return null;
    }


    @Override
    public Product getProductById(Long id) {

        if(redisTemplate.opsForHash().get(id, "PRODUCTS") != null){
            return (Product) redisTemplate.opsForHash().get(id, "PRODUCTS");
        }
        Product product = getProductFromFakeStoreProductDto(this.fakeStoreClient.getProductById(id));
        redisTemplate.opsForHash().put(id, "PRODUCTS", product);

        return product;
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.addProduct(getFakeStoreProductDtoFromProduct(product));
        Product createdProduct = getProductFromFakeStoreProductDto(fakeStoreProductDto);
        redisTemplate.opsForHash().put(createdProduct.getId(), "PRODUCTS", createdProduct);
        return createdProduct;
    }



    @Override
    public Product deleteProductById(Long id) {
        Product deletedProduct = getProductFromFakeStoreProductDto(this.fakeStoreClient.deleteProductById(id));
        redisTemplate.opsForHash().delete(deletedProduct.getId(), "PRODUCTS");
        return  deletedProduct;
    }

    @Override
    public Product updateProductById(Long id, Product product) {
        Product updatedProduct = getProductFromFakeStoreProductDto(this.fakeStoreClient.updateProductById(id, getFakeStoreProductDtoFromProduct(product)));
        redisTemplate.opsForHash().put(updatedProduct.getId(), "PRODUCTS", updatedProduct);
        return updatedProduct;
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

    private FakeStoreProductDto getFakeStoreProductDtoFromProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        return fakeStoreProductDto;
    }
}
