package com.example.SSGPaymtCertProject.util;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SSGUtils {
    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
