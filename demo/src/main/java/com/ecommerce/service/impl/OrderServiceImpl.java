package com.ecommerce.service.impl;

import com.ecommerce.exeption.InsufficientStockException;
import com.ecommerce.exeption.ProductNotFoundException;
import com.ecommerce.model.entity.OnlineOrder;
import com.ecommerce.model.entity.Product;
import com.ecommerce.model.entity.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public List<OnlineOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public OnlineOrder placeOrder(Long userId, Long productId, OnlineOrder onlineOrder) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        if (product.getStockQuantity() <= 0) {
            throw new InsufficientStockException("Product is out of stock! ");
        }
        if (onlineOrder.getRequestedQuantity() > product.getStockQuantity()) {
            throw new InsufficientStockException("Insufficient stock for placing the order!");
        }
        int newQuantity = product.getStockQuantity() - onlineOrder.getRequestedQuantity();
        product.setStockQuantity(newQuantity);

        onlineOrder.setOrderDate(LocalDateTime.now());
        onlineOrder.setProduct(product);
        onlineOrder.setUser(user);

        OnlineOrder savedOrder = orderRepository.save(onlineOrder);

        notifySellerByEmail(product.getSeller().getEmail(), product.getProductName(), onlineOrder.getRequestedQuantity());
        return savedOrder;
    }

    private void notifySellerByEmail(String sellerEmail, String productName, int requestedQuantity) {
        emailService.sendOrderNotification(sellerEmail, productName, requestedQuantity);
    }

    public List<OnlineOrder> getCustomerOrderHistory(String customerUsername) {
        return orderRepository.findByUserUsername(customerUsername);
    }

    @Override
    public String deletePlacedOrder(Long orderId) {
        OnlineOrder placedOrder = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Placed order not found with ID: " + orderId));
        Product product = placedOrder.getProduct();
        int newQuantity = product.getStockQuantity() + placedOrder.getRequestedQuantity();
        product.setStockQuantity(newQuantity);
        orderRepository.deleteById(orderId);
        productRepository.save(product);
        return "Placed order deleted successfully! ";
    }
}