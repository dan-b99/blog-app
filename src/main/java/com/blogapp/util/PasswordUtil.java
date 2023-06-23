package com.blogapp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class PasswordUtil {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String crypt(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean isMatch(String insertedPassword, String savedPassword) {
        return bCryptPasswordEncoder.matches(insertedPassword, savedPassword);
    }
}
