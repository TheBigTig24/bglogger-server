package com.example.bglogger.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bglogger.models.Follow;

@Repository 
public interface UserFollowsRepository extends JpaRepository<Follow, Integer> {
    
    Optional<Follow> findByFollowedIdAndFollowingId(Integer followedId, Integer followingId);
}
