package com.property.manager.rest.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
public abstract class AbstractDTO implements Serializable {

    @Positive
    @Nullable
    private Long id;
}
