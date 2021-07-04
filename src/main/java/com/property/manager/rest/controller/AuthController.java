package com.property.manager.rest.controller;

import com.property.manager.rest.dto.ClientDTO;
import com.property.manager.rest.dto.auth.AuthRequestDTO;
import com.property.manager.rest.dto.auth.RefreshTokenDTO;
import com.property.manager.rest.manager.AuthManager;
import com.property.manager.rest.uri.AuthUri;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthManager authManager;

    /**
     * Register to platform
     *
     * @param clientDTO DTO object
     * @return ResponseEntity object, with status code and object
     */
    @PostMapping(value = AuthUri.REGISTER)
    public ResponseEntity<?> register(@RequestBody @Validated ClientDTO clientDTO) {
        return authManager.register(clientDTO);
    }

    /**
     * Login to platform
     *
     * @param authRequestDTO DTO object
     * @return ResponseEntity object, with status code and object
     */
    @PostMapping(value = AuthUri.LOGIN)
    public ResponseEntity<?> login(@RequestBody @Validated AuthRequestDTO authRequestDTO) {
        return authManager.login(authRequestDTO);
    }

    /**
     * Refresh token
     *
     * @param refreshTokenDTO DTO object
     * @return ResponseEntity object, with status code and object
     */
    @GetMapping(value = AuthUri.REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(@RequestBody @Validated RefreshTokenDTO refreshTokenDTO) {
        return authManager.refreshToken(refreshTokenDTO);
    }
}




