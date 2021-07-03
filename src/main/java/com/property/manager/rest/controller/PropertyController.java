package com.property.manager.rest.controller;

import com.property.manager.rest.controller.common.BaseController;
import com.property.manager.rest.dto.PropertyDTO;
import com.property.manager.rest.manager.PropertyManager;
import com.property.manager.rest.uri.PropertyUri;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * ASets properties coordinates and saves in database
     *
     * @param propertyDTOs
     * @return ResponseEntity object, with status code and object
     */
    @PostMapping
    @PreAuthorize("permitAll")
    public ResponseEntity<?> addProperty(@RequestBody @Valid ArrayList<PropertyDTO> propertyDTOs) {
        return propertyManager.addProperty(propertyDTOs, getAuthenticated());
    }

    @PutMapping
    public ResponseEntity<?> addPropedrty(@RequestBody @Valid PropertyDTO propertyDTOs) {
        return null;
    }
}