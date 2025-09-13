package com.littleneighbors.shared.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericService<Req, Res, I> {
    Res create(Req request);
    Res findById(I id);
    Page<Res> findAll(Pageable pageable);
    Res update(I id, Req request);
    void  delete(I id);
}
