package com.property.manager.rest.controller;

import com.property.manager.rest.controller.common.BaseController;
import com.property.manager.rest.dto.PropertyDTO;
import com.property.manager.rest.manager.PropertyManager;
import com.property.manager.rest.uri.PropertyUri;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(PropertyUri.baseUri)
public class PropertyController extends BaseController {

    private final PropertyManager propertyManager;

    /**
     * Sets properties coordinates and saves
     *
     * @param propertyDTOs
     * @return ResponseEntity object, with status code and object
     */
    @PostMapping
    public ResponseEntity<?> addProperty(@RequestBody @Valid ArrayList<PropertyDTO> propertyDTOs) {
        return propertyManager.addProperty(propertyDTOs, getAuthenticated());
    }

    /**
     * Updates properties
     *
     * @param propertyDTOs
     * @return ResponseEntity object, with status code and object
     */
    @PutMapping
    public ResponseEntity<?> editProperties(@RequestBody @Valid ArrayList<PropertyDTO> propertyDTOs) {
        return propertyManager.updateProperty(propertyDTOs, getAuthenticated());
    }
}