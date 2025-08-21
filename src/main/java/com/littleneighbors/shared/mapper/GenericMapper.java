package com.littleneighbors.shared.mapper;

import java.util.List;

public interface GenericMapper<E, Req, Res> {

    E fromRequest(Req request);

    Res toResponse(E entity);

    List<Res> toResponseList(List<E> entities);
}

