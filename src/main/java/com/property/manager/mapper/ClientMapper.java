package com.property.manager.mapper;

import com.property.manager.data.entity.ClientEntity;
import com.property.manager.mapper.common.CommonDataMapper;
import com.property.manager.rest.dto.ClientDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

public class ClientMapper implements CommonDataMapper<ClientEntity, ClientDTO> {

    private final PasswordEncoder passwordEncoder;

    public ClientMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClientEntity toEntity(ClientDTO dto) {
        return new ClientEntity(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getName(),
                dto.getSurname());
    }

    @Override
    public Collection<ClientDTO> toDTOs(Collection<ClientEntity> entityList) {
        return null;
    }

    @Override
    public ClientDTO toDTO(ClientEntity entity) {
        return null;
    }

    @Override
    public Collection<ClientEntity> toEntities(Collection<ClientDTO> dtoList) {
        return null;
    }
}
