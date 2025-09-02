package com.littleneighbors.features.family.controller;

import com.littleneighbors.features.family.dto.FamilyLocationRequest;
import com.littleneighbors.features.family.dto.FamilyLocationResponse;
import com.littleneighbors.features.family.service.FamilyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/families/location")
public class FamilyLocationController {
    private final FamilyService familyService;

    public FamilyLocationController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("/me")
    public ResponseEntity<FamilyLocationResponse> getMyLocation() {
        Long currentUserId = 1L;
        FamilyLocationResponse response = familyService.getLocation(currentUserId);
        return  ResponseEntity.ok(familyService.getLocation(currentUserId));
    }
    @PutMapping("/me")
    public ResponseEntity<FamilyLocationResponse> updateMyLocation(
            @RequestBody FamilyLocationRequest request) {
        Long currentId = 1L;
        FamilyLocationResponse response = familyService.updateLocation(currentId, request);
        return ResponseEntity.ok(familyService.updateLocation(currentId, request));
    }
}
