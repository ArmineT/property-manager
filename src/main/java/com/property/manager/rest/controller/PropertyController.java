package com.property.manager.rest.controller;

import com.property.manager.rest.controller.common.BaseController;
import com.property.manager.rest.dto.PropertyDTO;
import com.property.manager.data.service.PropertyService;
import com.property.manager.rest.uri.PropertyUri;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(PropertyUri.baseUri)
public class PropertyController extends BaseController {

    private final PropertyService propertyService;

    /**
     * Sets properties coordinates and saves
     *
     * @param propertyDTOs
     * @return ResponseEntity object, with status code and object
     */
    @PostMapping
    public ResponseEntity<?> addProperty(@RequestBody @Validated ArrayList<PropertyDTO> propertyDTOs) {
        return propertyService.addProperty(propertyDTOs, getAuthenticated());
    }

    /**
     * Updates properties
     *
     * @param propertyDTOs
     * @return ResponseEntity object, with status code and object
     */
    @PutMapping
    public ResponseEntity<?> editProperties(@RequestBody @Validated ArrayList<PropertyDTO> propertyDTOs) {
        return propertyService.updateProperty(propertyDTOs, getAuthenticated());
    }

    /**
     * Gets properties of the authenticated user by pageable
     *
     * @param pageable
     * @return ResponseEntity object, with status code and objects
     */
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: field,(asc/desc), eg. id,ASC. " +
                            "Default sort order is descending by createdAt")
    })
    @GetMapping
    public ResponseEntity<?> getProperties(
            @ApiIgnore @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return propertyService.getAll(pageable, getAuthenticated());
    }
}