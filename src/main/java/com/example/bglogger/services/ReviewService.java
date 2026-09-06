package com.example.bglogger.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.bglogger.dto.ExternalGameDTO;
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
        this.gameService = gameService;
    }

    @Transactional
    public Review createNewReview(ReviewDTO dto) {
        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow();
        Optional<Game> foundGame = gameRepository.findByExternalId(dto.getGameId());

        Game game;

        if (!foundGame.isPresent()) {
            ExternalGameDTO gameData = gameService.getExternalGame(dto.getGameId()).getGames().get(0);

            Game newGame = new Game();
            newGame.setExternalId(Integer.parseInt(gameData.getId()));
            newGame.setName(gameData.getPrimaryName());
            newGame.setMinPlayers(gameData.getMinPlayers());
            newGame.setMaxPlayers(gameData.getMaxPlayers());
            newGame.setPlayingTime((gameData.getMinPlayingTime() + gameData.getMaxPlayingTime()) / 2);
            newGame.setGenre(gameData.getCategories());
            newGame.setCoverImageUrl(gameData.getImage());
            newGame.setLastSyncedAt(LocalDateTime.now());

            game = gameRepository.save(newGame);
        } else {
            game = foundGame.get();
        }

        Review reviewToPost = new Review();
        reviewToPost.setGame(game);
        reviewToPost.setUser(foundUser);
        reviewToPost.setBody(dto.getBody());
        reviewToPost.setScoreCategory(dto.getScoreCategory());
        reviewToPost.setListPosition(dto.getListPosition());
        reviewToPost.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(reviewToPost);
    }
}
