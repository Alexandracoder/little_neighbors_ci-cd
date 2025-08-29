package com.littleneighbors.features.neighborhood.model;

import com.littleneighbors.shared.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "neighborhoods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  Neighborhood implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "Neighborhood id must be greater than 0")
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    @NotNull(message = "Is a required field")
    private String cityName;


    @Column(nullable = false, unique = true, length = 255)
    @NotNull(message = "Is a required field")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Is a required field")
    private String streetName;
}
