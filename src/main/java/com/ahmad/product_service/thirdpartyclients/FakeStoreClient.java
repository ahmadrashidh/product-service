package com.ahmad.product_service.thirdpartyclients;

import com.ahmad.product_service.dtos.FakeStoreProductDto;
import com.ahmad.product_service.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FakeStoreClient {

    private RestTemplateBuilder restTemplateBuilder;

    private String productUrl = "https://fakestoreapi.com/products";
    private String singleProductUrl = productUrl + "/{id}";


    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeStoreProductDto getProductById(Long id) throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity(singleProductUrl, FakeStoreProductDto.class, id);
        if(responseEntity.getBody() == null)
            throw new ProductNotFoundException("Product not found:" + id);

        return responseEntity.getBody();
    }

    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.getForEntity(productUrl, FakeStoreProductDto[].class);

        return List.of(responseEntity.getBody());
    }

}
