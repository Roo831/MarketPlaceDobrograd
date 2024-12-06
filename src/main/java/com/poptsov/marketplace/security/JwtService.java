package com.poptsov.marketplace.security;


import com.poptsov.marketplace.database.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@Setter
public class JwtService {

    @Value("${marketplace.app.secret}")
    private String jwtSigningKey;

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        log.info("Payload generated...");
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("role", customUserDetails.getRole());
            claims.put("firstname", customUserDetails.getFirstname());
            claims.put("lastname", customUserDetails.getLastname());
            claims.put("isAdmin", customUserDetails.getIsAdmin());
        }
        String token = generateToken(claims, userDetails);
        log.info("Token generated for user: {}", userDetails.getUsername());
        return token;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("Checking if token is valid...");
        final String userName = extractUserName(token);
        boolean isValid = (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
        log.info("Is token valid = {}", isValid);
        return isValid;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        log.info("Extracting claim...");
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.info("Generating token...");
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

    boolean isTokenExpired(String token) {
        log.info("Checking if token is expired...");
        return extractExpiration(token).before(new Date());
    }

    Date extractExpiration(String token) {
        log.info("Extracting expiration date...");
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        log.info("Extracting all claims...");
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Failed to extract claims from token: {}", e.getMessage());
            throw e;
        }
    }

    Key getSigningKey() {
        log.info("Getting signing key...");
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        if (keyBytes.length * 8 < 256) {
            log.error("Key must be at least 256 bits");
            throw new IllegalArgumentException("Ключ должен быть не менее 256 бит!");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateTokenWithExpiredDate(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        log.info("Payload generated...");
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("role", customUserDetails.getRole());
            claims.put("firstname", customUserDetails.getFirstname());
            claims.put("lastname", customUserDetails.getLastname());
            claims.put("isAdmin", customUserDetails.getIsAdmin());
        }
        String token = generateExpiredToken(claims, userDetails);
        log.info("Expired token generated for user: {}", userDetails.getUsername());
        return token;
    }

    private String generateExpiredToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.info("Generating expired token...");
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() - 100000 * 60 * 24)) // Просрочка токена на 40 часов
                .signWith(getSigningKey())
                .compact();
    }

}
