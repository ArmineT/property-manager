package com.property.manager.rest.dto.error;

import com.property.manager.rest.dto.AbstractDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessageDTO<T extends Enum> extends AbstractDTO {
    private T fieldType;
    private String fieldName;
    private String message;


    public ErrorMessageDTO(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
