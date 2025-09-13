package com.littleneighbors.features.family.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.littleneighbors.features.child.dto.ChildResponse;
import com.littleneighbors.shared.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyResponse {
    private Long id;
    private String representativeName;
    private Long neighborhoodId;
    private List<Long> childrenIds;
    private List<ChildInfo> children;
    }



