package com.littleneighbors.features.family.service.impl;

import com.littleneighbors.features.neighborhood.entity.Neighborhood;
import com.littleneighbors.features.neighborhood.repository.NeighborhoodRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Transactional
class HealthCheckIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    @Test
    void contextLoads() {
        // Este test verifica que el contexto de Spring Boot se carga sin errores.
        // Si este test pasa, la configuración de la base de datos es correcta.
        assertThat(context).isNotNull();
    }

    @Test
    void databaseConnection_isWorking() {
        // Este test verifica que podemos guardar y recuperar una entidad de la base de datos.
        // Esto confirma que ddl-auto=create-drop y la conexión funcionan.
        Neighborhood newNeighborhood = Neighborhood.builder()
                .name("Test DB Connection")
                .cityName("Test City")
                .streetName("Test Street")
                .postalCode("00000")
                .build();

        Neighborhood savedNeighborhood = neighborhoodRepository.save(newNeighborhood);
        assertThat(savedNeighborhood).isNotNull();
        assertThat(savedNeighborhood.getId()).isNotNull();

        Optional<Neighborhood> foundNeighborhood = neighborhoodRepository.findById(savedNeighborhood.getId());
        assertThat(foundNeighborhood).isPresent();
        assertThat(foundNeighborhood.get().getName()).isEqualTo("Test DB Connection");
    }
}