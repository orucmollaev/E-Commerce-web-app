package com.ecommerce.controller;

import com.ecommerce.model.entity.Rating;
import com.ecommerce.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/{productId}")
    public List<Rating> getRatingsByProductId(@PathVariable Long productId) {
        return ratingService.getRatingsByProductId(productId);
    }

    @GetMapping("/getByName/{productName}")
    public List<Rating> getRatingsByProductName(@PathVariable Long productName) {
        return ratingService.getRatingsByProductId(productName);
    }

    @PostMapping("/addRating")
    public ResponseEntity<String> addRating(@RequestBody Rating rating) {
        return ratingService.addRating(rating);
    }
}