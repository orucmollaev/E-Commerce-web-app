package com.ecommerce.service;

import com.ecommerce.model.entity.OnlineOrder;

import java.util.List;

public interface OrderService {
    List<OnlineOrder> getAllOrders();

    OnlineOrder placeOrder(Long userId, Long productId, OnlineOrder orderDetails);

    String deletePlacedOrder(Long orderId);

    List<OnlineOrder> getCustomerOrderHistory(String customerUsername);
}