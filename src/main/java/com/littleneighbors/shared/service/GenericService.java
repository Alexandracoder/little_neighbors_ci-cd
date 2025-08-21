package com.littleneighbors.shared.service;

import java.util.List;

public interface GenericService<Req, Res, I> {
    Res create(Req request);
    Res findById(I id);
    List<Res>findAll();
    Res update(Long id, Req request);
    void  delete(Long id);

    Res update(I id, Req request);

    void delete(I id);
}
