package com.brigita.dragons.of.mugloar.clients.dto;

public record ShopResultDto(
        Boolean shoppingSuccess,
        int gold,
        int lives,
        int level,
        int turn
) {
}
