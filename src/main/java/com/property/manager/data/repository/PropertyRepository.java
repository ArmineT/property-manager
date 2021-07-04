package com.property.manager.data.repository;

import com.property.manager.data.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    PropertyEntity findByIdAndRemovedIsFalse(Long id);

    Collection<PropertyEntity> findAllByIdInAndRemovedIsFalse(Collection<Long> ids);
}