package com.djorquab.jarvis.technicaltest.mappers;

import java.util.List;

public interface AbstractMapper<E, D> {
    E dtoToEntity(D dto);
    D entityToDTO(E entity);
    List<E> dtosToEntities(List<D> dtos);
    List<D> entitiesToDTOs(List<E> entities);
}
