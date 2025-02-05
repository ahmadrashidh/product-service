package com.ahmad.product_service.security.dtos;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private String name;
    private String emailAddress;
    private String hashedPassword;
    private List<Role> roles = new ArrayList<>();
    private boolean isEmailVerified;
}
