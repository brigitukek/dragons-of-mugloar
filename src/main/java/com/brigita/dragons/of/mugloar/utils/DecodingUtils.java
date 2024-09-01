package com.brigita.dragons.of.mugloar.utils;

import java.util.Base64;

public class DecodingUtils {

    public static String decode(String probability, Integer encrypted) {
        return switch (encrypted) {
            case 1 -> decodeBase64(probability);
            case 2 -> decodeRot13(probability);
            case null, default -> probability;
        };
    }

    private static String decodeBase64(String encoded) {
        byte[] decoded = Base64.getDecoder().decode(encoded);
        return new String(decoded);
    }

    private static String decodeRot13(String encoded) {
        StringBuilder decoded = new StringBuilder();
        for (char c : encoded.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                decoded.append((char) ('a' + (c - 'a' + 13) % 26));
            } else if (c >= 'A' && c <= 'Z') {
                decoded.append((char) ('A' + (c - 'A' + 13) % 26));
            } else {
                decoded.append(c);
            }
        }
        return decoded.toString();
    }
}
