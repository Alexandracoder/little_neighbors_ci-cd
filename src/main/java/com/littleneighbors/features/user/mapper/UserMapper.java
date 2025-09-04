package com.littleneighbors.features.user.mapper;

import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.features.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

       public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        return response;
    }

    public User fromRequest(UserRequest request) {
           User user = new User();
           user.setEmail(request.getEmail());
           user.setPassword(request.getPassword());
           return user;
    }
}