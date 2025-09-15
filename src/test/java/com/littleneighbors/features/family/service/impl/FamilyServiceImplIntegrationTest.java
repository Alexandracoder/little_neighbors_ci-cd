package com.littleneighbors.features.family.service.impl;

import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.family.service.FamilyService;
import com.littleneighbors.features.neighborhood.entity.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.features.user.entity.User;
import com.littleneighbors.features.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.littleneighbors.features.user.entity.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class FamilyServiceImplIntegrationTest {

    @Autowired
    private FamilyService familyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    @Autowired
    private FamilyRepository familyRepository;

    private User testUser;
    private Neighborhood testNeighborhood;

    @BeforeEach
    void setup() {

        testUser = User.builder()
                .email("testuser9@example.com")
                .password("password")
                .role(ROLE_USER)
                .build();
        testUser = userRepository.save(testUser);

        testNeighborhood = Neighborhood.builder()
                .name("Test Neighborhood")
                .cityName("Test City")
                .streetName("123 Test Street")
                .postalCode("00001")
                .build();
        testNeighborhood = neighborhoodRepository.save(testNeighborhood);
    }

    @Test
    void createFamily_integrationTest() {

        FamilyRequest request = FamilyRequest.builder()
                .userId(testUser.getId())
                .neighborhoodId(testNeighborhood.getId())
                .representativeName("Integration Family")
                .familyName("IntegrationFamilyName")
                .description("Integration Test")
                .build();

        FamilyResponse response = familyService.create(request);

        assertThat(response).isNotNull();
        assertThat(response.getRepresentativeName()).isEqualTo("Integration Family");

        FamilyResponse familyFromDb = familyService.getFamilyByUserId(testUser.getId());
        assertThat(familyFromDb).isNotNull();
        assertThat(familyFromDb.getRepresentativeName()).isEqualTo("Integration Family");
        assertThat(familyFromDb.getNeighborhoodId()).isEqualTo(testNeighborhood.getId());
    }
}