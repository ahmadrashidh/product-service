package com.ahmad.product_service.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Category extends Base implements Serializable {

    private String name;

}
