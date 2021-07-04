package com.property.manager.rest.manager;

import com.property.manager.data.entity.ClientEntity;
import com.property.manager.data.entity.PropertyEntity;
import com.property.manager.data.service.GeoService;
import com.property.manager.data.service.PropertyService;
import com.property.manager.mapper.PropertyMapper;
import com.property.manager.rest.dto.PropertyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyManager {

    private final PropertyMapper propertyMapper;
    private final GeoService geoService;
    private final PropertyService propertyService;
    private final MessageSource messageSource;

    @Value("property.dto.required")
    private String property_dto_required;

    @Value("try.later")
    private String try_later;

    /**
     * Sets properties coordinates and saves in database
     *
     * @param propertyDTOs
     * @return ResponseEntity object, with status code and object
     */
    public ResponseEntity<?> addProperty(ArrayList<PropertyDTO> propertyDTOs, ClientEntity clientEntity) {
        try {
            geoService.setPropertiesCoordinates(propertyDTOs);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Collection<PropertyEntity> propertyEntities = propertyMapper.toEntities(propertyDTOs);
        propertyEntities.forEach(propertyEntity -> propertyEntity.setClientEntity(clientEntity));
        propertyEntities = (Collection<PropertyEntity>) propertyService.saveAll(propertyEntities);
        return new ResponseEntity<>(propertyMapper.toDTOs(propertyEntities), HttpStatus.OK);
    }

    /**
     * Updates properties, sets updated coordinates and saves in database
     *
     * @param propertyDTOs
     * @return ResponseEntity object, with status code and object
     */
    public ResponseEntity<?> updateProperty(ArrayList<PropertyDTO> propertyDTOs, ClientEntity clientEntity) {
        //checking if all propertyDTOs have id
        if (propertyDTOs.stream().
                anyMatch(propertyEntity -> propertyEntity.getId() == null))
            return new ResponseEntity<>(messageSource.getMessage(property_dto_required, null, null), HttpStatus.BAD_REQUEST);

        Collection<PropertyEntity> propertyEntities = propertyService.findAllByIdsAndRemovedIsFalse(
                propertyDTOs.stream().map(PropertyDTO::getId).collect(Collectors.toList()));

        //checking if propertyDTOs are client's
        if (!propertyEntities.stream().
                allMatch(propertyEntity -> propertyEntity.getClientEntity().getId().equals(clientEntity.getId())))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);


        //Filtering DTOs which have corresponding entities
        HashMap<Long, PropertyDTO> propertyDTOLongHashMap = new HashMap<>();
        propertyDTOs.forEach(propertyDTO -> propertyDTOLongHashMap.put(propertyDTO.getId(), propertyDTO));
        List<PropertyDTO> propertyDTOsFromEntities =
                propertyEntities.stream().
                        map(propertyEntity -> propertyDTOLongHashMap.get(propertyEntity.getId())).collect(Collectors.toList());
        propertyDTOs = (ArrayList<PropertyDTO>) propertyDTOsFromEntities;


        try {
            geoService.setPropertiesCoordinates(propertyDTOs);
        } catch (IOException | InterruptedException ex) {
            return new ResponseEntity<>(messageSource.getMessage(try_later, null, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        propertyMapper.updateToEntities(propertyDTOs, (ArrayList<PropertyEntity>) propertyEntities);
        propertyEntities.forEach(propertyEntity -> propertyEntity.setClientEntity(clientEntity));
        propertyEntities = (Collection<PropertyEntity>) propertyService.saveAll(propertyEntities);
        return new ResponseEntity<>(propertyMapper.toDTOs(propertyEntities), HttpStatus.OK);
    }
}
