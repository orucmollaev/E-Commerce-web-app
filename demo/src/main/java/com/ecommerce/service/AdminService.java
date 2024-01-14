package com.ecommerce.service;

import com.ecommerce.model.entity.Role;
import com.ecommerce.model.entity.User;

import java.util.List;

public interface AdminService {
    List<Role> getAllCustomers();

    List<Role> getAllSellers();

    List<User> getAllUsers();
}