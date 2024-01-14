package com.ecommerce.repository;

import com.ecommerce.model.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionTokenRepository extends JpaRepository<SessionToken, Long> {
}