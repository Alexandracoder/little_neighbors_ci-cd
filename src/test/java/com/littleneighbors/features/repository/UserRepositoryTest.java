package com.littleneighbors.features.repository;

import com.littleneighbors.features.user.model.User;
import com.littleneighbors.features.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    private @NotNull User createTestUser() {
        User user = new User();
        user.setEmail("picopico@prueba.com");
        user.setPassword("password123");
        return user;

    }

    @Test
    void testSaveUserCallsRepository() {
        User user = createTestUser();
        userRepository.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_whenEmailAlreadyExists_ShouldThrowException() {
        User existingUser = createTestUser();
        User newUser = createTestUser();
        when(userRepository.save((newUser)))
                .thenThrow(new IllegalArgumentException("Email already exists"));
        assertThrows(IllegalArgumentException.class,() -> {
            userRepository.save(newUser);
        });

    }

    @Test
    void createUser_whenPasswordIsIncorrect_shouldThrowException() {
        User newUser = createTestUser();
        when((userRepository.save(newUser)))
                .thenThrow((new IllegalArgumentException("Password Is Incorrect")));
        assertThrows(IllegalArgumentException.class, () -> {

                userRepository.save(newUser);

        });
    }

    @Test
    void updateUser_whenUserDoesNotExists_shouldThrowException() {
        User userToUpdate = createTestUser();
        when(userRepository.save(userToUpdate))
        .thenThrow(new IllegalArgumentException("User does not exists"));
        assertThrows(IllegalArgumentException.class, () -> {
            userRepository.save(userToUpdate);

        });

    }



}
