package com.example.bglogger.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "games")
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "min_players")
    private int minPlayers;

    @Column(name = "max_players")
    private int maxPlayers;

    @Column(name = "playing_time")
    private int playingTime;

    @Column(name = "complexity")
    private float complexity;

    @Column(name = "genre")
    private String[] genre;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

}
