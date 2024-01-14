package com.ecommerce.service.impl;

import com.ecommerce.exeption.InvalidUsernameOrPasswordException;
import com.ecommerce.exeption.UserAlreadyExistsException;
import com.ecommerce.exeption.UsernameNotFoundException;
import com.ecommerce.jwt.JwtService;
import com.ecommerce.model.dto.LoginDto;
import com.ecommerce.model.entity.Role;
import com.ecommerce.model.entity.SessionToken;
import com.ecommerce.model.entity.User;
import com.ecommerce.repository.SessionTokenRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionTokenRepository sessionTokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User registrationAsAdmin(User user) {
        return registrationAsAuthority(user, "ROLE_ADMIN");
    }

    @Override
    public User registrationAsCustomer(User user) {
        return registrationAsAuthority(user, "ROLE_CUSTOMER");
    }

    @Override
    public User registrationAsSeller(User user) {
        return registrationAsAuthority(user, "ROLE_SELLER");
    }

    public User registrationAsAuthority(User user, String authority) {
        if (userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUsername(user.getUsername()))
            throw new UserAlreadyExistsException("Username or email already exists! ");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase().trim());
        user.setUsername(user.getUsername().toLowerCase().trim());

        Role role = new Role();
        role.setAuthority(authority);
        role.setUser(user);
        user.setAuthorities(Set.of(role));
        return userRepository.save(user);
    }

    @Override
    public String login(LoginDto loginDto) {
        String username = loginDto.getUsername();
        if (!userRepository.existsByUsername(loginDto.getUsername())) {
            throw new InvalidUsernameOrPasswordException("Invalid username or password!");
        }
        String password = loginDto.getPassword();
        User user = userRepository.findByUsername(username).get();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Successful retrieval of the username!");
        if (passwordEncoder.matches(password, user.getPassword())) {
            log.info("The entered password is correct. Transaction successful!! ");

            String token = jwtService.generateToken(user.getUsername());
            SessionToken sessionToken = new SessionToken();

            sessionToken.setUserId(user.getId());
            sessionToken.setAccessToken(token);
            sessionToken.setCreateDate(LocalDateTime.now());
            sessionTokenRepository.save(sessionToken);
            return "Login successful! ";
        } else {
            return "Invalid credentials! ";
        }
    }

    @Override
    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect! ");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}