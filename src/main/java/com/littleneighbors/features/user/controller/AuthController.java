
package com.littleneighbors.features.user.controller;

import com.littleneighbors.config.security.JwtProvider;
import com.littleneighbors.features.user.dto.LoginRequest;
import com.littleneighbors.features.user.dto.JwtResponse;
import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.features.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;


    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtProvider.generateToken(userDetails);

        return ResponseEntity.ok("Login successful. Token: " + jwt);
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {

        UserResponse newUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}