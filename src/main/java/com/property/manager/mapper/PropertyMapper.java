package com.property.manager.mapper;

import com.property.manager.data.entity.PropertyEntity;
import com.property.manager.mapper.common.CommonDataMapper;
import com.property.manager.rest.dto.PropertyDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class PropertyMapper implements CommonDataMapper<PropertyEntity, PropertyDTO> {

    @Override
    public PropertyEntity toEntity(PropertyDTO dto) {
        PropertyEntity propertyEntity = new PropertyEntity();
        updateToEntity(dto, propertyEntity);
        return propertyEntity;
    }

    public void updateToEntity(PropertyDTO dto, PropertyEntity propertyEntity) {
        propertyEntity.setId(dto.getId());
        propertyEntity.setName(dto.getName());
        propertyEntity.setCountry(dto.getCountry());
        propertyEntity.setCity(dto.getCity());
        propertyEntity.setStreet(dto.getStreet());
        propertyEntity.setNumber(dto.getNumber());
        propertyEntity.setPostalCode(dto.getPostalCode());
        propertyEntity.setDescription(dto.getDescription());
        propertyEntity.setLat(dto.getLat());
        propertyEntity.setLon(dto.getLon());
    }

    @Override
    public Collection<PropertyDTO> toDTOs(Collection<PropertyEntity> entityList) {
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PropertyDTO toDTO(PropertyEntity entity) {
        PropertyDTO propertyDTO = new PropertyDTO(
                entity.getName(),
                entity.getCountry(),
                entity.getCity(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getPostalCode(),
                entity.getDescription(),
                entity.getLon(),
                entity.getLat());

        propertyDTO.setId(entity.getId());

        return propertyDTO;
    }

    @Override
    public Collection<PropertyEntity> toEntities(Collection<PropertyDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());

    }

    public void updateToEntities(ArrayList<PropertyDTO> dtos, ArrayList<PropertyEntity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            updateToEntity(dtos.get(i), entities.get(i));
        }
    }

}
