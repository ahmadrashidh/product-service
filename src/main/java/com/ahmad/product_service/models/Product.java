package com.ahmad.product_service.models;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends Base{

    private String name;

    private String description;

    private Double price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    private String image;
}
