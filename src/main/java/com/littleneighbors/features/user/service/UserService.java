package com.littleneighbors.features.user.service;


import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
