package com.example.bglogger.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bglogger.models.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    
    Optional<Game> findByExternalId(Integer externalId);
}
