package com.littleneighbors.features.neighborhood.service.impl;

import com.littleneighbors.features.family.entity.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;

import com.littleneighbors.features.neighborhood.dto.NeighborhoodRequest;
import com.littleneighbors.features.neighborhood.dto.NeighborhoodResponse;
import com.littleneighbors.features.neighborhood.mapper.NeighborhoodMapper;
import com.littleneighbors.features.neighborhood.entity.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.features.neighborhood.service.NeighborhoodServiceImpl;
import com.littleneighbors.shared.GenericServiceTestBase;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NeighborhoodServiceImplTest extends GenericServiceTestBase<NeighborhoodRequest, NeighborhoodResponse, Neighborhood, NeighborhoodServiceImpl> {
    @Mock
    private NeighborhoodRepository neighborhoodRepository;
    @Mock
    private FamilyRepository familyRepository;
    @Mock
    private NeighborhoodMapper mapper;

    @BeforeEach
    void setupService() {
        service = new NeighborhoodServiceImpl(neighborhoodRepository, familyRepository, mapper);
    }


    @Override
    protected NeighborhoodRequest buildRequest() {
        return NeighborhoodRequest.builder()
                .cityName("Valencia")
                .name("Mislata")
                .streetName("Archena")
                .build();
    }

    @Override
    protected Neighborhood buildEntity() {
        return Neighborhood.builder()
                .id(9L)
                .cityName("Valencia")
                .name("Mislata")
                .streetName("Mislata")
                .build();
    }

    @Override
    protected NeighborhoodResponse buildResponse() {
        return NeighborhoodResponse.builder()
                .id(9L)
                .cityName("Valencia")
                .name("Mislata")
                .streetName("Mislata")
                .build();
    }

    @Override
    protected Page<NeighborhoodResponse> getPageFromService() {
        Pageable pageable = PageRequest.of(0, 10);
        Neighborhood entity = buildEntity();
        NeighborhoodResponse response = buildResponse();

        Page<Neighborhood> page = new org.springframework.data.domain.PageImpl<>(List.of(entity), pageable, 1);
        when(neighborhoodRepository.findAll(pageable)).thenReturn(page);

        when(mapper.toResponse(entity)).thenReturn(response);

        return service.getNeighborhoods(pageable);
    }
    @Override
    protected NeighborhoodResponse getByIdFromService(Long id) {
        Neighborhood entity = buildEntity();
        NeighborhoodResponse response = buildResponse();

        Family family = Family.builder()
                .id(100L)
                .neighborhood(entity)
                .build();
        when(familyRepository.findByUserId(id)).thenReturn(Optional.of(family));
        when(mapper.toResponse(entity)).thenReturn(response);
        return service.getNeighborhoodByUserId(id);
    }

    @Override
    protected void assertThrowsFromService(Long id) {
        when(familyRepository.findByUserId(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getNeighborhoodByUserId(id));

    }

    @Override
    protected void mockCreateBehavior(NeighborhoodRequest request, Neighborhood entity, NeighborhoodResponse response) {
        when(mapper.fromRequest(request)).thenReturn(entity);
        when(neighborhoodRepository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

    }

    @Override
    protected NeighborhoodResponse invokeCreate(NeighborhoodRequest request) {
        return service.create(request);
    }

    @Override
    protected void verifyCreateInteractions(Neighborhood entity) {
        verify(mapper).fromRequest(any(NeighborhoodRequest.class));
        verify(neighborhoodRepository).save(entity);
        verify(mapper).toResponse(entity);

    }

    @Override
    protected Long getExistingId() {
        return 9L;
    }

    @Override
    protected Long getNonExistingId() {
        return 999L;
    }

    @Test
    void testCreate_happy() {
        NeighborhoodRequest request = buildRequest();
        Neighborhood entity = buildEntity();
        NeighborhoodResponse response = buildResponse();

        mockCreateBehavior(request, entity, response);
        NeighborhoodResponse result = invokeCreate(request);

        assertEquals(response.getId(), result.getId());
        assertEquals(response.getName(), result.getName());
        verifyCreateInteractions(entity);
    }

    @Test
    void testGetNeighborhoodByUserId_Happy() {
        Neighborhood neighborhood = buildEntity();
        Family family = new Family();
        family.setNeighborhood(neighborhood);
        when(familyRepository.findByUserId(getExistingId()))
                .thenReturn(Optional.of(family));
        when(mapper.toResponse(neighborhood))
                .thenReturn(buildResponse());

        NeighborhoodResponse result = service.getNeighborhoodByUserId(getExistingId());

        assertEquals(buildResponse().getId(),result.getId());
        assertEquals(buildResponse().getName(),result.getName());

    }

    @Test
    void testGetNeighborhoodByUserId_FamilyNotFound(){
        when(familyRepository.findByUserId(getNonExistingId()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getNeighborhoodByUserId(getNonExistingId()));
    }

    @Test
    void getNeighborhoodByUserId_NeighborhoodNull() {
        Family family = new Family();
        family.setNeighborhood(null);
        when(familyRepository.findByUserId(getExistingId()))
                .thenReturn(Optional.of(family));

        assertThrows(ResourceNotFoundException.class, () -> service.getNeighborhoodByUserId(getExistingId()));
    }

    @Test
    void testUpdateEntityFromRequest_Happy() {
        NeighborhoodRequest request = buildRequest();
        Neighborhood existing = buildEntity();
        when(neighborhoodRepository.findById(getExistingId()))
                .thenReturn(Optional.of(existing));
        when(neighborhoodRepository.save(existing))
                .thenReturn(existing);
        when(mapper.toResponse(existing))
                .thenReturn(buildResponse());
       NeighborhoodResponse result = service.update(getExistingId(), request);

        assertEquals(request.getCityName(), existing.getCityName());
        assertEquals(request.getName(), existing.getName());
        assertEquals(request.getStreetName(), existing.getStreetName());

        assertEquals(buildResponse().getId(), result.getId());

    }
    }
