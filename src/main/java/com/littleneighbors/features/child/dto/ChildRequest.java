package com.littleneighbors.features.child.dto;

import com.littleneighbors.features.child.entity.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildRequest {
    @NotNull(message = "Gender is required")
    private  Gender gender;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;
}
