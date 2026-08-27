package com.example.bglogger.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FollowId implements Serializable {
    
    @Column(name = "following_user_id")
    private Integer followingId;

    @Column(name = "followed_user_id")
    private Integer followedId;

    public FollowId() {};
    
    public FollowId(int followingId, int followedId) {
        this.followingId = followingId;
        this.followedId = followedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FollowId followId = (FollowId) o;
        return Objects.equals(followingId, followId.followingId) && 
            Objects.equals(followedId, followId.followedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followingId, followedId);
    }
}
