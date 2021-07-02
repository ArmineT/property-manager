package com.property.manager.rest.dto.error;

import lombok.Getter;

@Getter
public class ErrorDTO {
    private String message;

    public ErrorDTO(String message) {
        this.message = message;
    }
}
