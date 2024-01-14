package com.ecommerce.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull(message = "Product name is required! ")
    String productName;

    @NotBlank @NotNull(message = "Product description is required! ")
    String description;

    @NotNull(message = "Product price is required! ")
    BigDecimal price;

    @NotNull(message = "Product quantity is required! ")
    int stockQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<Rating> rating;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}