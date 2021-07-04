package com.property.manager.data.service;

import com.property.manager.data.entity.PropertyEntity;
import com.property.manager.data.repository.PropertyRepository;
import com.property.manager.data.service.common.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyService implements DataService<PropertyEntity, Long> {
    private final PropertyRepository propertyRepository;

    @Override
    public JpaRepository<PropertyEntity, Long> repository() {
        return propertyRepository;
    }

    public Collection<PropertyEntity> findAllByIdsAndRemovedIsFalse(Collection<Long> ids) {
        return propertyRepository.findAllByIdInAndRemovedIsFalse(ids);
    }

    public Collection<PropertyEntity> findAllByClientEntityIdAndRemovedFalse(Long clientEntityId, Pageable pageable) {
        return propertyRepository.findAllByClientEntityIdAndRemovedFalse(clientEntityId, pageable);
    }
}
