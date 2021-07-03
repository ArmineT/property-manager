package com.property.manager.rest.manager;

import com.property.manager.data.entity.ClientEntity;
import com.property.manager.data.entity.PropertyEntity;
import com.property.manager.data.service.GeoService;
import com.property.manager.data.service.PropertyService;
import com.property.manager.mapper.PropertyMapper;
import com.property.manager.rest.dto.PropertyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyManager {

    private final PropertyMapper propertyMapper;
    private final GeoService geoService;
    private final PropertyService propertyService;

    /**
     * ASets properties coordinates and saves in database
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
}
