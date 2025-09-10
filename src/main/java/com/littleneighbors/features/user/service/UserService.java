package com.littleneighbors.features.user.service;


import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.shared.service.GenericService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends GenericService<UserRequest, UserResponse, Long> {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
