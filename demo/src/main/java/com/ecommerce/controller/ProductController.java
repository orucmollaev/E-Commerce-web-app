package com.ecommerce.controller;

import com.ecommerce.model.entity.Product;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    final private ProductService productService;

    @GetMapping("/allProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/{productName}", method = RequestMethod.GET)
    public Product getProductByName(@PathVariable String productName) {
        return productService.getProductByName(productName);
    }

    @GetMapping("/getById/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getByProductId(productId);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product newProduct) {
        return productService.addProduct(newProduct);
    }

    @PutMapping("/change/product/{id}")
    public Product changeProduct(@RequestBody Product changedProduct, @PathVariable Long id) {
        return productService.changeProduct(changedProduct, id);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteByProductId(@PathVariable Long productId) {
        productService.deleteByProductId(productId);
    }
}