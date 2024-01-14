package com.ecommerce.controller;

import com.ecommerce.model.dto.ChangePasswordDto;
import com.ecommerce.model.dto.LoginDto;
import com.ecommerce.model.entity.User;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registerAsAdmin")
    public User registerAsAdmin(@RequestBody User user) {
        return userService.registrationAsAdmin(user);
    }

    @PostMapping("/registerAsCustomer")
    public User registerAsCustomer(@RequestBody User user) {
        return userService.registrationAsCustomer(user);
    }

    @PostMapping("/registerAsSeller")
    public User registerAsSeller(@RequestBody User user) {
        return userService.registrationAsSeller(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto request, Authentication authentication) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                String currentUsername = authentication.getName();
                List<String> allowedRoles = Arrays.asList("ROLE_SELLER", "ROLE_CUSTOMER", "ROLE_ADMIN");
                if (authentication.getAuthorities().stream().anyMatch(a -> allowedRoles.contains(a.getAuthority()))) {
                    User user = (User) authentication.getPrincipal();
                    userService.changePassword(user.getId(), request.getCurrentPassword(), request.getNewPassword());
                    return new ResponseEntity<>("The password has been successfully changed for: " + currentUsername, HttpStatus.OK);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied! ");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required! ");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to change password: " + e.getMessage());
        }
    }
}