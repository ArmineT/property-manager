package com.property.manager.security.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse implements Serializable {
    private static final long serialVersionUID = -6624726180748515507L;

    private String token;
    private String refreshToken;
}
