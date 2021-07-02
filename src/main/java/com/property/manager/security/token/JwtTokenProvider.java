package com.property.manager.security.token;

import com.auth0.jwt.JWT;
import com.property.manager.data.entity.ClientEntity;
import com.property.manager.error.CustomError;
import com.property.manager.error.CustomException;
import com.property.manager.data.service.ClientService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenProvider {

    private final ClientService clientService;

    @Value("${security.token.secret}")
    private String secretKey;

    @Value("${security.token.expire}")
    private Long tokenExpireIn;

    @Value("${security.refresh.token.expire}")
    private Long refreshTokenExpireIn;

    @Value("${security.token.header}")
    private String tokenHeader;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Builds compact URL-safe JWT string form username and createdAt
     *
     * @param username
     * @param createdAt
     * @return A compact URL-safe JWT string
     */
    public String createToken(String username, Date createdAt) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("createdAt", createdAt);
        claims.put("username", username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenExpireIn);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * @param username
     * @param createdAt
     * @return
     */
    public String refreshToken(String username, Date createdAt) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("createdAt", createdAt);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenExpireIn);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * By token generates authentication object
     *
     * @param token
     * @return UsernamePasswordAuthenticationToken object with clientEntity
     */
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        ClientEntity clientEntity = clientService.findByUsernameAndRemovedIsFalse(username);
        return new UsernamePasswordAuthenticationToken(clientEntity, "", null);
    }

    /**
     * From token gets username object
     *
     * @param token
     * @return Username
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Returns the value of the specified request header as a String
     *
     * @param req HttpServletRequest
     * @return a String containing the value of the requested header, or null if the request does not have a header of that name
     */
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader(tokenHeader);
    }

    /**
     * checks if the access token has valid pattern
     *
     * @param token
     * @return CustomExaption EXPIRED_TOKEN or ACCESS_DENIED message
     */

    public boolean validateToken(String token) throws CustomException {
        CustomException illegalCustomException = new CustomException(CustomError.EXPIRED_TOKEN);
        CustomException forbiddenCustomException = new CustomException(CustomError.ACCESS_DENIED);
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                throw forbiddenCustomException;
            }
            return true;
        } catch (JwtException | IllegalArgumentException | CustomException e) {
            try {
                if (JWT.decode(token).getExpiresAt().before(new Date()))
                    throw forbiddenCustomException;
                return true;
            } catch (Exception ex) {
                throw illegalCustomException;
            }
        }
    }
}
