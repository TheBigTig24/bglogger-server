package com.example.bglogger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bglogger.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
}
