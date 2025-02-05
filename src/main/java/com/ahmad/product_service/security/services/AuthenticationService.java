package com.ahmad.product_service.security.services;

import com.ahmad.product_service.security.dtos.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {

    private RestTemplate restTemplate;
    private String url = "http://localhost:9000/users/validate/";

    public AuthenticationService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public boolean authenticate(String token){
        ResponseEntity<User> userResponse = restTemplate.postForEntity(url, null, User.class , token);
        return userResponse.getBody() != null;
    }
}
