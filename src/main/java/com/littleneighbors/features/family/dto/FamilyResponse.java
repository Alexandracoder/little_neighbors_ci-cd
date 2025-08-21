package com.littleneighbors.features.family.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyResponse {
    private Long id;
    private String representativeName;
    private String neighborhood;
    private String area;
    private List<Long> childrenIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


