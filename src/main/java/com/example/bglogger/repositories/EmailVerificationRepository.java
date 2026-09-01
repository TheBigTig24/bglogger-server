package com.example.bglogger.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bglogger.models.EmailVerification;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Integer> {
    
    Optional<EmailVerification> findByToken(String token);
}
