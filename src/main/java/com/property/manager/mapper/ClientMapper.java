package com.property.manager.mapper;

import com.property.manager.data.entity.ClientEntity;
import com.property.manager.mapper.common.CommonDataMapper;
import com.property.manager.rest.dto.ClientDTO;
import com.property.manager.utils.EncryptionUtils;

import java.util.Collection;

public class ClientMapper implements CommonDataMapper<ClientEntity, ClientDTO> {
    private final EncryptionUtils encryptionUtils;

    public ClientMapper(EncryptionUtils encryptionUtils) {
        this.encryptionUtils = encryptionUtils;
    }

    @Override
    public ClientEntity toEntity(ClientDTO dto) {
        return new ClientEntity(
                dto.getUsername(),
                encryptionUtils.encrypt(dto.getPassword()),
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
