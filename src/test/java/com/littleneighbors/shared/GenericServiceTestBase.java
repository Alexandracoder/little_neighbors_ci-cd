package com.littleneighbors.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @param <REQ>
 * @param <RES>
 * @param <ENTITY>
 * @param <SERVICE>
 */

public abstract class GenericServiceTestBase<REQ,  RES, ENTITY, SERVICE> {
    protected SERVICE service;

    @BeforeEach
    void initMocks() {

        MockitoAnnotations.openMocks(this);
    }

    protected abstract REQ buildRequest();
    protected abstract ENTITY buildEntity();
    protected abstract RES buildResponse();
    protected abstract Page <RES> getPageFromService();
    protected abstract RES getByIdFromService(Long id);
    protected abstract void assertThrowsFromService(Long id);
    protected abstract void mockCreateBehavior(REQ request, ENTITY entity, RES response);
    protected abstract RES invokeCreate(REQ request);
    protected abstract void verifyCreateInteractions(ENTITY entity);
    protected abstract Long getExistingId();
    protected abstract Long getNonExistingId();


    @Test
    void testGetAll() {
        Page<RES> page = getPageFromService();
        assertThat(page).isNotNull();
    }

    @Test
    void testGetById_Happy() {
         RES response = getByIdFromService(getExistingId());
        assertThat(response).isNotNull();

    }



    @Test
    void testGetById_Unhappy() {
        assertThrowsFromService(getNonExistingId());
    }



    @Test
    void testCreate_generic() {
        REQ request = buildRequest();
        ENTITY entity = buildEntity();
        RES response = buildResponse();

        mockCreateBehavior(request, entity, response);

        RES result = invokeCreate(request);

        assertThat(result).isNotNull();
        verifyCreateInteractions(entity);

    }
}













