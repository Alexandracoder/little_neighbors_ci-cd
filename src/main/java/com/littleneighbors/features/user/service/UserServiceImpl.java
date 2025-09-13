package com.littleneighbors.features.user.service;

import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.features.user.exception.DuplicateResourceException;
import com.littleneighbors.features.user.mapper.UserMapper;
import com.littleneighbors.features.user.entity.Role;
import com.littleneighbors.features.user.entity.User;
import com.littleneighbors.features.user.repository.UserRepository;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponse createUser(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw  new DuplicateResourceException("Email already Exists");
        }
        User user = new User();
        user.setEmail((request.getEmail()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    // Other methods remain the same...
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id ));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (!user.getEmail().equals(request.getEmail())&& userRepository.existsByEmail(request.getEmail())) {
            throw  new DuplicateResourceException("Email already exists: " + request.getEmail());
        }
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with id: " + id));

        userRepository.delete(user);
    }
}

