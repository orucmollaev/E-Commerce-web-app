package com.ecommerce.config;

import com.ecommerce.filter.JwtAuthFilter;
import com.ecommerce.service.impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailServiceImpl userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, "/users/registerAsCustomer", "/users/registerAsSeller", "/users/login","/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/changePassword").hasAnyRole("CUSTOMER", "SELLER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/delete/{productId}").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/users/allCustomers", "/users/allSellers", "/users/allUsers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products/allProducts", "/products/{productId}", "/products/{productName}").hasAnyRole("CUSTOMER", "SELLER")
                        .requestMatchers(HttpMethod.POST, "/products/change/product/{id}").hasRole("SELLER")
                        .requestMatchers(HttpMethod.POST, "/orders/placeOrder/{userId}/{productId}").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/products/add").hasRole("SELLER")
                        .requestMatchers(HttpMethod.GET, "/ratings/{productId}", "/orders/orderHistory").hasAnyRole("CUSTOMER", "SELLER")
                        .requestMatchers(HttpMethod.GET, "/orders/orderHistory").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/ratings/addRating").hasRole("CUSTOMER")
                        .anyRequest().permitAll())
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}