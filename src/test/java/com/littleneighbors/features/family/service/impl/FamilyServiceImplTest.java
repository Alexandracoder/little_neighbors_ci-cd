package com.littleneighbors.features.family.service.impl;

import com.littleneighbors.features.child.repository.ChildRepository;
import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.mapper.FamilyMapper;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.family.repository.FamilyRepository;
import com.littleneighbors.features.family.service.FamilyServiceImpl;
import com.littleneighbors.features.neighborhood.model.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import com.littleneighbors.features.user.model.User;
import com.littleneighbors.features.user.repository.UserRepository;
import com.littleneighbors.shared.GenericServiceTestBase;
import com.littleneighbors.shared.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

public class FamilyServiceImplTest extends GenericServiceTestBase<FamilyRequest, FamilyResponse, Family, FamilyServiceImpl> {
    @Mock private FamilyRepository familyRepository;
    @Mock private NeighborhoodRepository neighborhoodRepository;
    @Mock private UserRepository userRepository;
    @Mock private FamilyMapper mapper;
    @Mock private ChildRepository childRepository;

    private FamilyRequest request;
    private Family entity;
    private FamilyResponse response;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new FamilyServiceImpl(familyRepository, neighborhoodRepository,userRepository, childRepository, mapper);

        request = new FamilyRequest();
        request.setUserId(1L);
        request.setNeighborhoodId(10L);
        request.setRepresentativeName("Juan");
        request.setDescription("Test Family");

        entity = new Family();
        entity.setRepresentativeName(request.getRepresentativeName());

        response = new FamilyResponse();
        response.setRepresentativeName(request.getRepresentativeName());
    }
    @Override
    protected FamilyRequest buildRequest() { return request; }
    @Override
    protected Family buildEntity() { return entity; }
    @Override
    protected FamilyResponse buildResponse() { return  response; }
    @Override
    protected Long getExistingId() { return  1L; }
    @Override
    protected Long getNonExistingId() { return 999L; }

    @Override
    protected Page<FamilyResponse> getPageFromService() {
        when(familyRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toResponse(entity)).thenReturn(response);
        return service.getFamilies(Pageable.unpaged());
    }

    @Override
    protected FamilyResponse getByIdFromService(Long id) {
        when(familyRepository.findByUserId(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);
        return service.getFamilyByUserId(id);

    }

    @Override
    protected void assertThrowsFromService(Long id) {
        when(familyRepository.findByUserId(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getFamilyByUserId(id));

    }

    @Override
    protected void mockCreateBehavior(FamilyRequest request, Family family, FamilyResponse response) {
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(new User()));
        when(neighborhoodRepository.findById(request.getNeighborhoodId())).thenReturn(Optional.of(new Neighborhood()));
        when(mapper.fromRequest(request)).thenReturn(entity);
        when(familyRepository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

    }

    @Override
    protected FamilyResponse invokeCreate(FamilyRequest request) {
        return service.create(request);

    }

    @Override
    protected void verifyCreateInteractions(Family family) {
        verify(userRepository).findById(request.getUserId());
        verify(neighborhoodRepository).findById(request.getNeighborhoodId());
        verify(familyRepository).save(entity);
        verify(mapper).toResponse(entity);

    }

    @Test
    void create_userNotFound_throws() {
        request.setUserId(999L);
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.create(request));
    }

    @Test
    void create_neighborhoodNotFound_throws() {
        request.setNeighborhoodId(888L);
         when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(new User()));
         when(neighborhoodRepository.findById(request.getNeighborhoodId())).thenReturn(Optional.empty());
         when(mapper.fromRequest(request)).thenReturn(new Family());
        when(mapper.toResponse(any(Family.class))).thenReturn(new FamilyResponse());

        assertThrows(ResourceNotFoundException.class, () -> service.create(request));

    }

    @Test
    void updateFamily_withValidRequest_updatesSuccessfully() {
        Long familyId = 1L;
        Family existing = new Family();
        existing.setId(familyId);
        existing.setRepresentativeName("Old Name");

        FamilyRequest request = new FamilyRequest();
        request.setRepresentativeName("New Name");
        request.setNeighborhoodId(10L);

        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setId(10L);

        Family updated = new Family();
        updated.setId(familyId);
        updated.setRepresentativeName("New Name");
        updated.setNeighborhood(neighborhood);

        FamilyResponse expectedResponse = new FamilyResponse();
        expectedResponse.setRepresentativeName("New Name");

        when(familyRepository.findById(familyId)).thenReturn(Optional.of(existing));
        when(neighborhoodRepository.findById(request.getNeighborhoodId())).thenReturn(Optional.of(neighborhood));
        when(familyRepository.save(existing)).thenReturn(updated);
        when(mapper.toResponse(updated)).thenReturn(expectedResponse);

        FamilyResponse result = service.updateFamily(familyId, request);

        assertThat(result)
                .isNotNull()
                .extracting(FamilyResponse::getRepresentativeName)
                .isEqualTo("New Name");

        verify(familyRepository).findById(familyId);
        verify(neighborhoodRepository).findById(request.getNeighborhoodId());
        verify(familyRepository).save(existing);
        verify(mapper).toResponse(updated);
        verifyNoMoreInteractions(familyRepository, neighborhoodRepository, mapper);
    }
    @Test
    void getFamilies_withMultipleEntities_returnsPageCorrectly() {
        Family entity2 = new Family();
        entity2.setRepresentativeName("Alex Rojas");

        FamilyResponse response2 = new FamilyResponse();
        response2.setRepresentativeName("Alex Rojas");

        when(familyRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity, entity2 )));

        when(mapper.toResponse(entity)).thenReturn(response);
        when(mapper.toResponse(entity2)).thenReturn(response2);

        Page<FamilyResponse> page = service.getFamilies(Pageable.unpaged());

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent()).contains(response, response2);

    }

    @Test
    void updateFamily_familyNotFound_throws() {
        Long familyId = 99L;
        FamilyRequest request = new FamilyRequest();
        request.setRepresentativeName("New Name");
        request.setNeighborhoodId(1L);

        when(familyRepository.findById(familyId)).thenReturn(Optional.empty());
         assertThatThrownBy(() -> service.updateFamily(familyId,request))
                 .isInstanceOf(ResourceNotFoundException.class)
                 .hasMessageContaining("Family not found with id " + familyId);

        verify(familyRepository).findById(familyId);
         verifyNoMoreInteractions(familyRepository, neighborhoodRepository, mapper);

    }

    @Test
    void updateFamily_neighborhoodNotFound_throws() {
        Long familyId = 1L;
        Family existing = new Family();
        existing.setId(familyId);

        FamilyRequest request = new FamilyRequest();
        request.setRepresentativeName("New Name");
        request.setNeighborhoodId(99L);

        when(familyRepository.findById((familyId))).thenReturn(Optional.of(existing));
        when(neighborhoodRepository.findById(request.getNeighborhoodId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateFamily(familyId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Neighborhood not found with id " + request.getNeighborhoodId());

        verify(familyRepository).findById(familyId);
        verify(neighborhoodRepository).findById(request.getNeighborhoodId());
        verifyNoMoreInteractions(familyRepository, neighborhoodRepository, mapper);

    }
}






