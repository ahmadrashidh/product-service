package com.ahmad.product_service.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends Base{

    private String name;

    private String description;

    private Long price;

    private Category category;

    private String image;
}
