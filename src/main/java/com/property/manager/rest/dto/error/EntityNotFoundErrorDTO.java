package com.property.manager.rest.dto.error;

import java.text.MessageFormat;

public class EntityNotFoundErrorDTO extends ErrorDTO {
    public EntityNotFoundErrorDTO(String entityName) {
        super(MessageFormat.format("{0} does not exist", entityName));
    }

    public EntityNotFoundErrorDTO(String entityName, Long id) {
        super(MessageFormat.format("{0} with id {1} does not exist", entityName, id));
    }

    public EntityNotFoundErrorDTO(String entityName, String id) {
        super(MessageFormat.format("{0} with data {1} does not exist", entityName, id));
    }
}
