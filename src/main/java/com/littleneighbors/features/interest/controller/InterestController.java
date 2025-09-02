package com.littleneighbors.features.interest.controller;

import com.littleneighbors.features.interest.dto.InterestRequest;
import com.littleneighbors.features.interest.dto.InterestResponse;
import com.littleneighbors.features.interest.service.InterestServiceImpl;
import com.littleneighbors.shared.controller.GenericController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/interests")
public class InterestController extends GenericController<InterestRequest, InterestResponse, Long> {
    private final InterestServiceImpl interestService;

    public InterestController(InterestServiceImpl interestService) {
        super(interestService);
        this.interestService = interestService;

    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<InterestResponse>> getInterestByChildId(@PathVariable Long childId) {
        return ResponseEntity.ok(interestService.getInterestByChild(childId));
    }
}