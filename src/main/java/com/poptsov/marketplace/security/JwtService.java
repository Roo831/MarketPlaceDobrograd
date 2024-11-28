package com.poptsov.marketplace.security;


import com.poptsov.marketplace.database.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

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
        return generateToken(claims, userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.info("is Token Valid?");
        final String userName = extractUserName(token);
        boolean isValid = (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
        log.info("is Token Valid = {}", isValid);
        return isValid;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        log.info("extract Claim...");
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        log.info("Generate token...");
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey()).compact();
    }

    private boolean isTokenExpired(String token) {
        log.info("is Token Expired?");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.info("Extract expiration...");
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        log.info("Extract all claims...");
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        log.info("Get signing key...");
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        if (keyBytes.length * 8 < 256) {
            log.info("Key must be at least 256 bytes");
            throw new IllegalArgumentException("Ключ должен быть не менее 256 бит!");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
