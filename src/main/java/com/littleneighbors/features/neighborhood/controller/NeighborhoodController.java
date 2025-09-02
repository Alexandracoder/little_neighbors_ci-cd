package com.littleneighbors.features.neighborhood.controller;

import com.littleneighbors.features.neighborhood.dto.NeighborhoodRequest;
import com.littleneighbors.features.neighborhood.dto.NeighborhoodResponse;
import com.littleneighbors.features.neighborhood.service.NeighborhoodServiceImpl;
import com.littleneighbors.shared.controller.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/neighborhoods")
public class NeighborhoodController extends GenericController<NeighborhoodRequest, NeighborhoodResponse, Long> {

    public NeighborhoodController(NeighborhoodServiceImpl neighborhoodService) {
        super(neighborhoodService);
    }
}
