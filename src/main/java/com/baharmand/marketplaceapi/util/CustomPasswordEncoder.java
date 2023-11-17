package com.baharmand.marketplaceapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Custom password encoder using BCryptPasswordEncoder for encoding and matching passwords.
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Encodes the raw password using BCrypt algorithm.
     *
     * @param rawPassword the raw password to encode.
     * @return the encoded password.
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Checks if the raw password matches the encoded password.
     *
     * @param rawPassword     the raw password to check.
     * @param encodedPassword the encoded password to compare.
     * @return true if the passwords match, false otherwise.
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
