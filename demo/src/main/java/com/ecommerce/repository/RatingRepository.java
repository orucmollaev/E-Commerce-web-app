package com.ecommerce.repository;

import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> getRatingsByProductId(Long productId);
}