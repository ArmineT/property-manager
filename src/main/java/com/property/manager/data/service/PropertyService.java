package com.property.manager.data.service;

import com.property.manager.data.entity.PropertyEntity;
import com.property.manager.data.repository.PropertyRepository;
import com.property.manager.data.service.common.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyService implements DataService<PropertyEntity, Long> {
    private final PropertyRepository propertyRepository;

    @Override
    public JpaRepository<PropertyEntity, Long> repository() {
        return propertyRepository;
    }

    public PropertyEntity findByIdAndRemovedIsFalse(Long id) {
        return propertyRepository.findByIdAndRemovedIsFalse(id);
    }
}
