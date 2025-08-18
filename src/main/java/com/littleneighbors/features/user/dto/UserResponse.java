package com.littleneighbors.features.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String role;
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
