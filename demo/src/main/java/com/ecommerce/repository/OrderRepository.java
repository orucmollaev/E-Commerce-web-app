package com.ecommerce.repository;

import com.ecommerce.model.entity.OnlineOrder;
import com.ecommerce.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<OnlineOrder, Long> {
    List<OnlineOrder> findByUserUsername(String customerUsername);
}