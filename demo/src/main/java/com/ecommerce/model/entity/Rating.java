package com.ecommerce.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ratingId;
    Integer stars;
    LocalDateTime ratingDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}