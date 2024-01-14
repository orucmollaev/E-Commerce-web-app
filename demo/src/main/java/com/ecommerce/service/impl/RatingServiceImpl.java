package com.ecommerce.service.impl;

import com.ecommerce.exeption.ProductNotFoundException;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.Rating;
import com.ecommerce.repository.RatingRepository;
import com.ecommerce.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final ProductServiceImpl productService;

    public ResponseEntity<String> addRating(Rating rating) {
        try {
            if (rating.getStars() < 1 || rating.getStars() > 10) {
                throw new IllegalArgumentException("Rating must be between 1 and 10 stars! ");
            }
            Long productId = rating.getProduct().getId();
            Product product = productService.getByProductId(productId);
            if (product == null) {
                throw new ProductNotFoundException("Product not found with ID : " + productId);
            }
            rating.setProduct(product);
            ratingRepository.save(rating);
            return new ResponseEntity<>("Rating added successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    public List<Rating> getRatingsByProductId(Long productId) {
        return ratingRepository.getRatingsByProductId(productId);
    }
}