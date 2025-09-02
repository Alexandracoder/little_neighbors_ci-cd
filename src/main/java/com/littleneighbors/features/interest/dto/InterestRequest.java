package com.littleneighbors.features.interest.dto;

import com.littleneighbors.features.interest.model.InterestType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestRequest {
    @NotBlank(message =  "Interest name is required")
    private String name;

    @NotBlank(message = "Interest type is required")
    private InterestType type;
}
