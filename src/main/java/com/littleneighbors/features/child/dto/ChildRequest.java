package com.littleneighbors.features.child.dto;

import com.littleneighbors.features.child.model.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ChildRequest {
    @NotNull(message = "Gender is required")
    private  Gender gender;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;
}
