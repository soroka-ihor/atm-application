package io.codelions.services;

import io.codelions.entities.Card;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.Map;

public interface JwtService {

    Claims getAllClaimsFromToken(String token);

    Date getExpirationDateFromToken(String token);

    Boolean isTokenExpired(String token);

    String generateToken(Card card, String type);

    String generateTokenWithClaims(Map<String, Object> claims, String username, String type);

    Boolean validateToken(String token);
}
