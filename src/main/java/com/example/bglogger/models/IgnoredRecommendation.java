package com.example.bglogger.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ignored_recommendations")
public class IgnoredRecommendation {
    
    @EmbeddedId
    private UserGameId id;

    @Column(name = "dismissed_at")
    private LocalDateTime dismissedAt;

    @PrePersist
    protected void onCreate() {
        this.dismissedAt = LocalDateTime.now();
    }
}
