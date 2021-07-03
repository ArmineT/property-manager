package com.property.manager.rest.controller.common;

import com.property.manager.data.entity.ClientEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {

    public ClientEntity getAuthenticated() {
        ClientEntity clientEntity = (ClientEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientEntity;
    }
}