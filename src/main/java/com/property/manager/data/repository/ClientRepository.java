package com.property.manager.data.repository;

import com.property.manager.data.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    ClientEntity findByUsernameAndRemovedIsFalse(String username);

    ClientEntity findByUsernameAndPasswordAndRemovedFalse(String username, String password);
}