package com.ecommerce.controller;

import com.ecommerce.model.entity.OnlineOrder;
import com.ecommerce.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    private final OrderService orderService;

//    @PreAuthorize("")
    @RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
    public List<OnlineOrder> getOrderHistory(Principal principal) {
        String customerUsername = principal.getName();
        return orderService.getCustomerOrderHistory(customerUsername);
    }

    @RequestMapping(value = "/placeOrder/{userId}/{productId}", method = RequestMethod.POST)
    public OnlineOrder placeOrder(@PathVariable Long userId, @PathVariable Long productId, @RequestBody OnlineOrder orderDetails) {
        return orderService.placeOrder(userId, productId, orderDetails);
    }

    @RequestMapping(value = "/deletePlacedOrder/{orderId}", method = RequestMethod.DELETE)
    public String deletePlacedOrder(@PathVariable Long orderId) {
        return orderService.deletePlacedOrder(orderId);
    }
}