package com.ahmad.product_service.thirdpartyclients;

import com.ahmad.product_service.dtos.FakeStoreProductDto;
import com.ahmad.product_service.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FakeStoreClient {

    private RestTemplateBuilder restTemplateBuilder;

    private String productUrl;
    private String productUrlById;


    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder, @Value("${fakestoreapi.url.products}") String productsUrl, @Value("${fakestoreapi.url.products.byid}") String productsUrlByid ){

        this.restTemplateBuilder = restTemplateBuilder;
        this.productUrl = productsUrl;
        this.productUrlById = productsUrlByid;
    }

    public FakeStoreProductDto getProductById(Long id) throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity(productUrlById, FakeStoreProductDto.class, id);
        if(responseEntity.getBody() == null)
            throw new ProductNotFoundException("Product not found:" + id);

        return responseEntity.getBody();
    }

    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.getForEntity(productUrl, FakeStoreProductDto[].class);

        return List.of(responseEntity.getBody());
    }

    public FakeStoreProductDto addProduct(FakeStoreProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.postForEntity(productUrl, product, FakeStoreProductDto.class);
        return responseEntity.getBody() ;
    }

    public FakeStoreProductDto updateProductById(Long id, FakeStoreProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.patchForObject(productUrlById, product, FakeStoreProductDto.class, id);
    }

    public FakeStoreProductDto deleteProductById(Long id) {
        // writing own http request and response processing to facilitate returning entity - which is not provided by RestTemplate.delete()
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback reqCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.execute(productUrlById, HttpMethod.DELETE, reqCallback, responseExtractor, id );
        return responseEntity.getBody();
    }

}
