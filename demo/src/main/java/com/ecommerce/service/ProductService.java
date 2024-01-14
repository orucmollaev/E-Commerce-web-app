package com.ecommerce.service;

import com.ecommerce.model.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getByProductId(Long productId);

    Product getProductByName(String productName);

    ResponseEntity<String> addProduct(Product product);

    Product changeProduct(Product changedProduct, Long id);

    void deleteByProductId(Long productId);
}