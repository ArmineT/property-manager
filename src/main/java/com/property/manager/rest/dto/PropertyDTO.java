package com.property.manager.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyDTO extends AbstractDTO {

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String country;

    @NotNull
    @Size(max = 255)
    private String city;

    @NotNull
    @Size(max = 255)
    private String street;

    @NotNull
    private Integer number;

    @NotNull
    @Size(max = 255)
    private String postalCode;

    @Size(max = 255)
    private String description;

    private Double lat;

    private Double lon;
}