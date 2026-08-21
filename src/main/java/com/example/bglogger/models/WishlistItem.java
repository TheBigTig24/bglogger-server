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
@Table(name = "wishlist_items")
public class WishlistItem {
    
    @EmbeddedId
    private UserGameId id;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @Column(name = "priority")
    private int priority;

    @PrePersist
    protected void onCreate() {
        this.addedAt = LocalDateTime.now();
    }
}
