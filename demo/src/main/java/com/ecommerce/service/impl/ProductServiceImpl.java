package com.ecommerce.service.impl;

import com.ecommerce.exeption.ProductNotFoundException;
import com.ecommerce.exeption.UsernameNotFoundException;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getByProductId(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found! "));
    }

    @Override
    public Product getProductByName(String productName) {
        log.info("Product name is" + productName);
        return productRepository.getByProductName(productName);
    }

    @Override
    public ResponseEntity<String> addProduct(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sellerUsername = authentication.getName();
        User seller = userRepository.findByUsername(sellerUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found! "));
        product.setSeller(seller);
        productRepository.save(product);
        return ResponseEntity.ok("Product added successfully! ");
    }

    @Override
    public Product changeProduct(Product changedProduct, Long id) {
        if (changedProduct != null) {
            Optional<Product> existingProductId = productRepository.findById(id);
            if (existingProductId.isPresent()) {
                Product existingProduct = existingProductId.get();
                existingProduct.setProductName(changedProduct.getProductName());
                existingProduct.setDescription(changedProduct.getDescription());
                existingProduct.setPrice(changedProduct.getPrice());
                return productRepository.save(existingProduct);
            } else {
                throw new ProductNotFoundException("Product not found!");
            }
        }
        return null;
    }

    @Override
    public void deleteByProductId(Long productId) {
        log.info("Product and it's rating has been deleted! Product id was : " + productId);
        productRepository.deleteById(productId);
    }
}