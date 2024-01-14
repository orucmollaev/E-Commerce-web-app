package com.ecommerce.service;

import com.ecommerce.model.dto.LoginDto;
import com.ecommerce.model.entity.User;

import java.util.List;

public interface UserService {
    User registrationAsCustomer(User user);

    User registrationAsAdmin(User user);

    String login(LoginDto loginDto);

    User registrationAsSeller(User user);

    void changePassword(Long id, String currentPassword, String newPassword);
}