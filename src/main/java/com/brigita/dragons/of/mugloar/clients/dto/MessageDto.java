package com.brigita.dragons.of.mugloar.clients.dto;

public record MessageDto(
        String adId,
        String message,
        int reward,
        Integer encrypted,
        String probability,
        int expiresIn
) {
}
