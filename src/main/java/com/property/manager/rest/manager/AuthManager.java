package com.property.manager.rest.manager;

import com.property.manager.data.entity.ClientEntity;
import com.property.manager.data.service.ClientService;
import com.property.manager.error.CustomException;
import com.property.manager.mapper.ClientMapper;
import com.property.manager.rest.dto.ClientDTO;
import com.property.manager.rest.dto.auth.AuthRequestDTO;
import com.property.manager.rest.dto.auth.AuthResponseDTO;
import com.property.manager.rest.dto.auth.RefreshTokenDTO;
import com.property.manager.security.token.JwtTokenProvider;
import com.property.manager.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthManager {

    private final ClientService clientService;
    private final JwtTokenProvider jwtTokenProvider;
    private final EncryptionUtils encryptionUtils;
    private final HashMap<String, String> refreshTokens = new HashMap<>();
    private final MessageSource messageSource;

    @Value("client.username.exists")
    private String client_username_exists;

    @Value("incorrect.username.or.password")
    private String incorrect_username_or_password;

    /**
     * Register in platform
     *
     * @param clientDTO - OK status code
     */
    public ResponseEntity<?> register(@Valid ClientDTO clientDTO) {
        ClientEntity clientEntityWithSameUsername = clientService.findByUsernameAndRemovedIsFalse(clientDTO.getUsername());
        if (clientEntityWithSameUsername != null) {
            return new ResponseEntity<>(messageSource.getMessage(client_username_exists, null, null), HttpStatus.OK);
        }
        ClientMapper clientMapper = new ClientMapper(encryptionUtils);
        ClientEntity clientEntity = clientMapper.toEntity(clientDTO);
        clientService.save(clientEntity);

        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    /**
     * Login to platform
     *
     * @param authRequestDTO DTO object
     * @return ResponseEntity object, with
     * - OK status code and AuthDTO object or error message
     */
    public ResponseEntity<?> login(@Valid AuthRequestDTO authRequestDTO) {
        ClientEntity clientEntity = clientService.findByUsernameAndPasswordAndRemovedFalse(
                authRequestDTO.getUsername(), encryptionUtils.encrypt(authRequestDTO.getPassword()));
        if (clientEntity != null) {
            String token = jwtTokenProvider.createToken(clientEntity.getUsername(), clientEntity.getCreatedAt());
            String refreshToken = jwtTokenProvider.refreshToken(clientEntity.getUsername(), clientEntity.getCreatedAt());
            refreshTokens.put(refreshToken, authRequestDTO.getUsername());
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, refreshToken);
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    messageSource.getMessage(incorrect_username_or_password, null, null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Refresh valid token
     *
     * @param refreshTokenDTO DTO object
     * @return ResponseEntity object, with
     * - OK status code and AuthDTO object or UNAUTHORIZED status code and error message
     */
    public ResponseEntity<?> refreshToken(@Valid RefreshTokenDTO refreshTokenDTO) {
        String refreshToken = refreshTokenDTO.getRefreshToken();
        String username = refreshTokens.get(refreshTokenDTO.getRefreshToken());
        try {
            if (username != null
                    && username.equals(refreshTokenDTO.getUsername())
                    && jwtTokenProvider.validateToken(refreshToken)) {
                String token = jwtTokenProvider.createToken(refreshTokenDTO.getUsername(), new Date());
                AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, refreshTokenDTO.getRefreshToken());
                return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (CustomException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
