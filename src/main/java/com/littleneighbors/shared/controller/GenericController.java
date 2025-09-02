package com.littleneighbors.shared.controller;

import com.littleneighbors.shared.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class  GenericController<Req, Res, I> {

    protected final GenericService<Req, Res, I> genericService;

    public GenericController(GenericService<Req, Res, I> genericService) {
        this.genericService = genericService;
    }
    @PostMapping
    public ResponseEntity<Res> create(@RequestBody Req request) {
        Res response = genericService.create(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Res> findById(@PathVariable I id) {
        Res response = genericService.findById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<Page<Res>> findAll(Pageable pageable) {
        Page<Res> responsePage = genericService.findAll(pageable);
        return ResponseEntity.ok(responsePage);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Res> update(@PathVariable I id, @RequestBody Req request) {
        Res response = genericService.update(id, request);
        return ResponseEntity.ok(response);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable I id) {
        genericService.delete(id);
                return ResponseEntity.noContent().build();
    }
}
