package com.property.manager.mapper.common;

import java.util.Collection;

public interface CommonDataMapper<E, D> {
    E toEntity(D dto);

    Collection<D> toDTOs(Collection<E> entityList);

    D toDTO(E entity);

    Collection<E> toEntities(Collection<D> dtoList);

    E updateFromDTO(D dto, E entity);
}
