package com.brigita.dragons.of.mugloar.clients.dto;

public record GameDto(
        String gameId,
        int lives,
        int gold,
        int level,
        int score,
        int turn
) {
}
