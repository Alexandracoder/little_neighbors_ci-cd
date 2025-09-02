package com.littleneighbors.features.match.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchRequest {

    @NotNull(message = "The ID of the child requesting the match is required.")
    private Long requesterChildId;

    @NotNull(message = "The ID of the child receiving the match is required.")
    private Long receivingChildId;
}
