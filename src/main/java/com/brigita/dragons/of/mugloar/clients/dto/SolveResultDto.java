package com.brigita.dragons.of.mugloar.clients.dto;

import com.brigita.dragons.of.mugloar.data.Game;

public record SolveResultDto(
        Boolean success,
        int lives,
        int gold,
        int score,
        int turn,
        String message
) {
    public SolveResultDto(Boolean success, Game game) {
        this(success, game.getLives(), game.getGold(), game.getScore(), game.getTurn(), null);
    }

}
