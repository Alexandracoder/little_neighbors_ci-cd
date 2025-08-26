package com.littleneighbors.features.child.controller;

import com.littleneighbors.features.child.dto.ChildRequest;
import com.littleneighbors.features.child.dto.ChildResponse;
import com.littleneighbors.features.child.service.ChildService;
import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.service.FamilyService;
import com.littleneighbors.shared.controller.GenericController;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/children")
public class ChildController extends GenericController<ChildRequest, ChildResponse, Long> {
    private final ChildService childService;
    private final FamilyService familyService;

    public ChildController(ChildService childService, FamilyService familyService) {
        super(childService);
        this.childService = childService;

        this.familyService = familyService;
    }
    @GetMapping("/family/{familyId}")
    public ResponseEntity<Page<ChildResponse>> getChildrenByFamily(@PathVariable Long familyId, Pageable pageable) {

        Page<ChildResponse> response = childService.getChildrenByFamily(familyId, pageable);
        return  ResponseEntity.ok(response);

    }

    @GetMapping("/me")
    public ResponseEntity<Page<ChildResponse>> getMyChildren(Pageable pageable) {
        Long currentUserId = 1L;
        FamilyResponse myFamily = familyService.getFamilyByUserId(currentUserId);
        Page<ChildResponse> children = childService.getChildrenByFamily(myFamily.getId(), pageable);
        return ResponseEntity.ok(children);
    }

    @PostMapping("/family/{familyId}")
    public ResponseEntity<ChildResponse> addChildToFamily
            (@PathVariable Long familyId, @Valid
            @RequestBody ChildRequest request) {
        ChildResponse response = childService.addChildToFamily(familyId, request);
        return ResponseEntity.status(201).body(response);

    }

}
