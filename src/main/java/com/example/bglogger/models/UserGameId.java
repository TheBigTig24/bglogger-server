package com.example.bglogger.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserGameId implements Serializable {
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "game_id", nullable = false)
    private Integer gameId;

    public UserGameId() {};

    public UserGameId(int userId, int gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserGameId favGameId = (UserGameId) o;
        return Objects.equals(userId, favGameId.userId) && 
            Objects.equals(gameId, favGameId.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, gameId);
    }
}
