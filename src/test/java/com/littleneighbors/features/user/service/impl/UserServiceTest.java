package com.littleneighbors.features.user.service.impl;

import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.features.user.exception.DuplicateResourceException;
import com.littleneighbors.features.user.mapper.UserMapper;
import com.littleneighbors.features.user.entity.User;
import com.littleneighbors.features.user.repository.UserRepository;
import com.littleneighbors.features.user.service.UserServiceImpl;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder);

        userRequest = new UserRequest();
        userRequest.setEmail("picopico@prueba.com");
        userRequest.setPassword("password123");
    }

    private User createTestUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("picopico@prueba.com");
        user.setPassword(passwordEncoder.encode("password123"));
        return user;
    }

    private UserResponse createTestUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole() != null ? user.getRole().name() : null);
        return response;
    }

    @Test
    void createUser_success() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);

        User savedUser = createTestUser();
        UserResponse expectedResponse = createTestUserResponse(savedUser);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(expectedResponse);

        UserResponse response = userService.createUser(userRequest);

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getEmail(), response.getEmail());
        assertEquals(expectedResponse.getRole(), response.getRole());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_EmailExists_throwsException() {
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(userRequest));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_success() {
        User user = createTestUser();
        UserResponse expectedResponse = createTestUserResponse(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(expectedResponse);

        UserResponse response = userService.getUserById(user.getId());

        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getEmail(), response.getEmail());
        assertEquals(expectedResponse.getRole(), response.getRole());

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void getUserById_throwsException() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(id));

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void getAllUsers_success() {
        User user = createTestUser();
        UserResponse expectedResponse = createTestUserResponse(user);

        when(userRepository.findAll()).thenReturn(java.util.List.of(user));
        when(userMapper.toResponse(user)).thenReturn(expectedResponse);

        java.util.List<UserResponse> responseList = userService.getAllUsers();

        assertEquals(1, responseList.size());
        assertEquals(expectedResponse.getId(), responseList.get(0).getId());
        assertEquals(expectedResponse.getEmail(), responseList.get(0).getEmail());
        assertEquals(expectedResponse.getRole(), responseList.get(0).getRole());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUser_success() {
        User existingUser = createTestUser();

        UserRequest updateRequest = new UserRequest();
        updateRequest.setEmail("newmail@prueba.com");
        updateRequest.setPassword("newPassword123!");

        User updatedUser = createTestUser();
        updatedUser.setEmail(updateRequest.getEmail());
        updatedUser.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

        UserResponse expectedResponse = createTestUserResponse(updatedUser);

        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail(updateRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.toResponse(updatedUser)).thenReturn(expectedResponse);

        UserResponse response = userService.updateUser(existingUser.getId(), updateRequest);

        assertEquals(expectedResponse.getEmail(), response.getEmail());
        assertEquals(expectedResponse.getRole(), response.getRole());

        verify(userRepository, times(1)).findById(existingUser.getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser_success() {
        User existingUser = createTestUser();

        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        userService.deleteUser(existingUser.getId());

        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void deleteUser_notFound_throwsException() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(id));

        verify(userRepository, never()).delete(any(User.class));
    }
}


