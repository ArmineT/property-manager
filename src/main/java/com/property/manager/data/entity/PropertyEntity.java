package com.property.manager.data.entity;

import com.property.manager.data.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "property")
public class PropertyEntity extends BaseEntity {

    @Column(name = "name")
    protected String name;

    @Column(name = "country")
    protected String country;

    @Column(name = "city")
    protected String city;

    @Column(name = "street")
    protected String street;

    @Column(name = "number")
    protected Integer number;

    @Column(name = "postal_code")
    protected String postalCode;

    @Column(name = "description")
    protected String description;

    @Column(name = "lon")
    protected Double lon;

    @Column(name = "lat")
    protected Double lat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    private ClientEntity clientEntity;

    public PropertyEntity(String name, String country, String city, String street, Integer number,
                          String postalCode, String description, Double lon, Double lat) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }
}