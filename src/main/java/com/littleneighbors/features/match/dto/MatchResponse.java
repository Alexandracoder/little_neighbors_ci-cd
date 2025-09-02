package com.littleneighbors.features.match.dto;

import com.littleneighbors.features.match.model.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResponse {

    private Long id;
    private Long requesterChildId;
    private Long receiverChildId;
    private MatchStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
