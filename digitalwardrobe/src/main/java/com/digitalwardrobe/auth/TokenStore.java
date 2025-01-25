package com.digitalwardrobe.auth;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenStore {

    private Set<String> invalidatedTokens = new HashSet<>(); // holds invalid tokens after logout

    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    public boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
}