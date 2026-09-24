package com.example.bglogger.dto;

import com.example.bglogger.annotations.ValueOfEnum;
import com.example.bglogger.enumerations.FollowRequestStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class FollowRequestDTO {
    
    @NotNull(message = "Following ID is required")
    private Integer followingId;

    @NotNull(message = "Followed ID is required")
    private Integer followedId;

    @NotNull(message = "Status is required")
    @ValueOfEnum(enumClass = FollowRequestStatus.class, message = "Status must be PENDING, ACCEPTED, or DECLINED") 
    private String status;
}
