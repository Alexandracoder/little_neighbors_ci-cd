package com.littleneighbors.shared.mapper;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public abstract class GenericMapper<Req, Res, E> {

  public abstract   E fromRequest(@NotNull Req request);

    public abstract Res toResponse(E entity);

   public List<Res> toResponseList(List<E> entities) {
       if (entities == null) return List.of();
       return entities.stream()
               .map(this::toResponse)
               .collect(Collectors.toList());
   }

}