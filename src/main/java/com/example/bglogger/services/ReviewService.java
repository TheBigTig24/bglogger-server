package com.example.bglogger.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.bglogger.dto.ReviewDTO;
import com.example.bglogger.models.Game;
import com.example.bglogger.models.Review;
import com.example.bglogger.models.User;
import com.example.bglogger.repositories.GameRepository;
import com.example.bglogger.repositories.ReviewRepository;
import com.example.bglogger.repositories.UserRepository;


@Service
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final RestTemplate restTemplate;
    private final GameService gameService;

    public ReviewService(
        ReviewRepository reviewRepository,
        UserRepository userRepository,
        GameRepository gameRepository,
        RestTemplate restTemplate,
        GameService gameService
    ) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.restTemplate = restTemplate;
        this.gameService = gameService;
    }

    @Transactional
    public Review createNewReview(ReviewDTO dto) {
        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow();
        Optional<Game> foundGame = gameRepository.findByExternalId(dto.getGameId());

        if (!foundGame.isPresent()) {
            
        }
        return null;
    }
}
