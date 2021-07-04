package com.property.manager.data.repository;

import com.property.manager.data.entity.PropertyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    Collection<PropertyEntity> findAllByIdInAndRemovedIsFalse(Collection<Long> ids);

    List<PropertyEntity> findAllByClientEntityIdAndRemovedFalse(Long clientEntityId, Pageable pageable);
}