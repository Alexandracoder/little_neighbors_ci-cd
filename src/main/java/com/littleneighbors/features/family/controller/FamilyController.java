package com.littleneighbors.features.family.controller;

import com.littleneighbors.features.family.dto.FamilyRequest;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.service.FamilyService;
import com.littleneighbors.shared.controller.GenericController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/families")
public class FamilyController extends GenericController<FamilyRequest, FamilyResponse, Long> {
    private final FamilyService familyService;

    public FamilyController(FamilyService familyService) {
        super(familyService);
        this.familyService = familyService;
    }
    @GetMapping("/me")
    public ResponseEntity<FamilyResponse> getMyFamily() {
        Long currentUserId = 1L;
        FamilyResponse myFamily = familyService.getFamilyByUserId(currentUserId);//Aquí reemplazaré el user cuando tenga implemente seguridad//
        return ResponseEntity.ok(myFamily);
    }

}
