package com.littleneighbors.features.user.controller;

import com.littleneighbors.features.user.dto.UserRequest;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.shared.controller.GenericController;
import com.littleneighbors.shared.service.GenericService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operaciones sobre usuarios")
public class UserController extends GenericController<UserRequest, UserResponse, Long> {

    public UserController(GenericService<UserRequest, UserResponse, Long> genericService) {
        super(genericService);
    }
}
