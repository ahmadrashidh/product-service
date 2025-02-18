package com.ahmad.product_service.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class Base implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
}
