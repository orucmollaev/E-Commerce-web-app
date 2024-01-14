package com.ecommerce.service.impl;

import com.ecommerce.model.entity.Role;
import com.ecommerce.model.entity.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> getAllCustomers() {
        return roleRepository.findByAuthority("ROLE_CUSTOMER");
    }

    @Override
    public List<Role> getAllSellers() {
        return roleRepository.findByAuthority("ROLE_SELLER");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}