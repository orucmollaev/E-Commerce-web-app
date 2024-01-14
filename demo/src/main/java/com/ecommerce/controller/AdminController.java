package com.ecommerce.controller;

import com.ecommerce.model.entity.Role;
import com.ecommerce.model.entity.User;
import com.ecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/allCustomers")
    public List<Role> getAllCustomers() {
        return adminService.getAllCustomers();
    }

    @GetMapping("/allSellers")
    public List<Role> getAllSellers() {
        return adminService.getAllSellers();
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }
}