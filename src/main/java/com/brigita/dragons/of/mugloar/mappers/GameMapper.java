package com.brigita.dragons.of.mugloar.mappers;

import com.brigita.dragons.of.mugloar.clients.dto.GameDto;
import com.brigita.dragons.of.mugloar.clients.dto.ShopResultDto;
import com.brigita.dragons.of.mugloar.clients.dto.SolveResultDto;
import com.brigita.dragons.of.mugloar.data.Game;
import com.brigita.dragons.of.mugloar.data.Probability;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public Game mapFromDto(GameDto gameDto) {
        return new Game(gameDto.gameId(), gameDto.lives(), gameDto.level(), gameDto.gold(), gameDto.score(), gameDto.turn(), null);
    }

    public Game update(Game game, ShopResultDto shopResult) {
        return update(game, game.getLastProbability(), shopResult.lives(), shopResult.gold(), shopResult.turn(), game.getScore(), shopResult.level());
    }

    public Game update(Game game, SolveResultDto solveResult, Probability lastUsedProbability) {
        var probability = solveResult.success() ? lastUsedProbability : game.getLastProbability();
        return update(game, probability, solveResult.lives(), solveResult.gold(), solveResult.turn(), solveResult.score(), game.getLevel());
    }

    private Game update(Game game, Probability lastUsedProbability, int lives, int gold, int turn, int score, int level) {
        game.setLastProbability(lastUsedProbability);
        game.setLives(lives);
        game.setGold(gold);
        game.setScore(score);
        game.setTurn(turn);
        game.setLevel(level);
        return game;
    }
}
