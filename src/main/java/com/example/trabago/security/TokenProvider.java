package com.example.trabago.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider
{

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "trabago-backend";
    public static final String TOKEN_AUDIENCE = "trabago-frontend";

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration.minutes}")
    private Long jwtExpirationMinutes;

    public String generate(Authentication authentication)
    {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        byte[] signingKey = jwtSecret.getBytes();

        return Jwts.builder().setHeaderParam("token_type", TOKEN_TYPE).signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512).setExpiration(Date.from(ZonedDateTime.now().plusMinutes(jwtExpirationMinutes).toInstant())).setIssuedAt(Date.from(ZonedDateTime.now().toInstant())).setId(UUID.randomUUID().toString()).setIssuer(TOKEN_ISSUER).setAudience(TOKEN_AUDIENCE).setSubject(user.getUsername()).claim("user_role", roles).claim("name", user.getName()).claim("email", user.getEmail()).claim("id", user.getId()).compact();
    }

    public Optional<Jws<Claims>> validateTokenAndGetJws(String token)
    {
        try {
            byte[] signingKey = jwtSecret.getBytes();

            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException exception) {
            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (SignatureException exception) {
            log.error("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }
        return Optional.empty();
    }
}
