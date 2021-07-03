package com.property.manager.mapper;

import com.property.manager.data.entity.PropertyEntity;
import com.property.manager.mapper.common.CommonDataMapper;
import com.property.manager.rest.dto.PropertyDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Component
public class PropertyMapper implements CommonDataMapper<PropertyEntity, PropertyDTO> {

    @Override
    public PropertyEntity toEntity(PropertyDTO dto) {
        PropertyEntity PropertyEntity = new PropertyEntity(
                dto.getName(),
                dto.getCountry(),
                dto.getCity(),
                dto.getStreet(),
                dto.getNumber(),
                dto.getPostalCode(),
                dto.getDescription(),
                dto.getLat(),
                dto.getLon());
        return PropertyEntity;
    }

    @Override
    public Collection<PropertyDTO> toDTOs(Collection<PropertyEntity> entityList) {
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PropertyDTO toDTO(PropertyEntity entity) {
        return new PropertyDTO(
                entity.getName(),
                entity.getCountry(),
                entity.getCity(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getPostalCode(),
                entity.getDescription(),
                entity.getLon(),
                entity.getLat());
    }

    @Override
    public Collection<PropertyEntity> toEntities(Collection<PropertyDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());

    }

    @Override
    public PropertyEntity updateFromDTO(PropertyDTO dto, PropertyEntity entity) {
        return null;
    }

}
