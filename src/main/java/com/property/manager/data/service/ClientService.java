package com.property.manager.data.service;

import com.property.manager.data.entity.ClientEntity;
import com.property.manager.data.repository.ClientRepository;
import com.property.manager.data.service.common.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService implements DataService<ClientEntity, Long> {
    private final ClientRepository clientRepository;

    @Override
    public JpaRepository<ClientEntity, Long> repository() {
        return clientRepository;
    }

    public ClientEntity findByUsernameAndRemovedIsFalse(String username) {
        return clientRepository.findByUsernameAndRemovedIsFalse(username);
    }

    public ClientEntity findByUsernameAndPasswordAndRemovedFalse(String username, String password) {
        return clientRepository.findByUsernameAndPasswordAndRemovedFalse(username, password);
    }

}
