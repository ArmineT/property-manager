package com.property.manager.data.entity;

import com.property.manager.data.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "client")
public class ClientEntity extends BaseEntity {
    @Column(name = "username")
    protected String username;

    @Column(name = "password")
    protected String password;

    @Column(name = "name")
    protected String name;

    @Column(name = "surname")
    protected String surname;

}
