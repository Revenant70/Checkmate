package com.todo.backend.service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.todo.backend.repository.ResetToken;
import com.todo.backend.repository.UserEntity;

public class ResetTokenService {

    private static final Map<String, ResetToken> tokenMap = new ConcurrentHashMap<>();

    public static ResetToken generateToken(UserEntity userEntity, Date expiryDate) {
        String tokenValue = UUID.randomUUID().toString();
        ResetToken resetToken = new ResetToken();
        resetToken.setToken(tokenValue);
        resetToken.setUser(userEntity);
        resetToken.setExpiryDate(expiryDate);
        tokenMap.put(tokenValue, resetToken);
        return resetToken;
    }

    public static ResetToken getToken(String tokenValue) {
        return tokenMap.get(tokenValue);
    }

    public static void removeToken(String tokenValue) {
        tokenMap.remove(tokenValue);
    }

    public static void cleanUpExpiredTokens() {
        Date now = new Date();
        tokenMap.entrySet().removeIf(entry -> entry.getValue().getExpiryDate().before(now));
    }

    public static Date calculateExpiryDate() {
        return new Date(System.currentTimeMillis() + 1800000);
    }
}
