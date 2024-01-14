package com.ecommerce.service;

import com.ecommerce.model.entity.Rating;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RatingService {
    ResponseEntity<String> addRating(Rating rating);

    List<Rating> getRatingsByProductId(Long productId);
}