package com.littleneighbors.utils;

import com.littleneighbors.shared.AbstractGenericService;
import com.littleneighbors.shared.Identifiable;
import com.littleneighbors.shared.mapper.GenericMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public abstract class GenericServiceWithMapperTest<
        ENTITY extends Identifiable<ID>,
        REQUEST,
        RESPONSE,
        ID,
        REPOSITORY extends org.springframework.data.jpa.repository.JpaRepository<ENTITY, ID>,
        MAPPER extends GenericMapper<ENTITY, REQUEST, RESPONSE>,
        SERVICE extends AbstractGenericService<ENTITY, REQUEST, RESPONSE, ID>> {

    protected REPOSITORY repository;
    protected MAPPER mapper;
    protected SERVICE service;

    protected abstract SERVICE createService(REPOSITORY repository, MAPPER mapper);

    protected abstract ENTITY createEntity();
    protected abstract RESPONSE createResponse();
    protected abstract REQUEST createRequest();
    protected abstract ID getEntityId();

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(getRepositoryClass());
        mapper = Mockito.mock(getMapperClass());
        service = createService(repository, mapper);
    }

    protected abstract Class<REPOSITORY> getRepositoryClass();
    protected abstract Class<MAPPER> getMapperClass();

    //  Happy path: findById
    @Test
    void testFindById_HappyPath() {
        ENTITY entity = createEntity();
        RESPONSE response = createResponse();

        when(repository.findById(getEntityId())).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        RESPONSE result = service.findById(getEntityId());

        assertThat(result).isEqualTo(response);
        verify(repository).findById(getEntityId());
        verify(mapper).toResponse(entity);
    }

    // Sad path: entity not found
    @Test
    void testFindById_SadPath() {
        when(repository.findById(getEntityId())).thenReturn(Optional.empty());

        try {
            service.findById(getEntityId());
        } catch (RuntimeException e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
        }

        verify(repository).findById(getEntityId());
        verifyNoInteractions(mapper);
    }
}