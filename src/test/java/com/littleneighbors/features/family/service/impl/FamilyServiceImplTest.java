package com.littleneighbors.features.family.service.impl;

import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.mapper.FamilyMapper;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.family.service.FamilyServiceImpl;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FamilyServiceImplTest {
    @Mock
    private FamilyRepository familyRepository;

    @Mock
    private FamilyMapper familyMapper;

    @InjectMocks
    private FamilyServiceImpl familyService;

    private FamilyRequest familyRequest;
    private Family family;
    private FamilyResponse familyResponse;
    private Neighborhood neighborhood;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        familyRequest = new FamilyRequest();
        familyRequest.setRepresentativeName("Alexandra Rojas");
        familyRequest.setArea("Valencia");
        familyRequest.setUserId(1L);
        familyRequest.setNeighborhood("Mislata");

        neighborhood = Neighborhood.builder()
                .id(1L)
                .name("Mislata")
                .streetName("Archena")
                .build();

        family = new Family();
        family.setId(1L);
        family.setArea(familyRequest.getArea());
        family.setNeighborhood(neighborhood);
    }
    @Test
    void createFamily_success() {
        when(familyRepository.existsById(familyRequest.getUserId())).thenReturn(false);

        Family savedFamily = Family.builder()
                .id(1L)
                .representativeName(familyRequest.getRepresentativeName())
                .area(familyRequest.getArea())
                .neighborhood(neighborhood)
                .build();

        when(familyRepository.save(any(Family.class))).thenReturn(savedFamily);

    }


    }



