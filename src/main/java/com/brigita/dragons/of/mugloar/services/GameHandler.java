package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.data.Game;
import com.brigita.dragons.of.mugloar.mappers.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameHandler {

    private static final int GOLD_AMOUNT_TO_HAVE = 150;
    private static final int MAX_LIVES_TO_HAVE = 3;
    private static final int GAME_SCORE_GOAL = 1000;

    @Autowired
    private GameService gameService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private GameMapper gameMapper;

    public void playGame() {
        var game = gameService.startGame();
        System.out.println("Started to play game with id " + game.getId());

        do {
            shop(game);
            game = solveMessage(game);
            printGameStats(game);
        } while (!isGameFinished(game));

        printResults(game);
    }

    private boolean isGameFinished(Game game) {
        return game.getLives() == 0 || game.getScore() >= GAME_SCORE_GOAL;
    }

    private void shop(Game game) {
        buyPotion(game);
        sharpenClaws(game);
    }

    private void sharpenClaws(Game game) {
        if (game.getGold() < GOLD_AMOUNT_TO_HAVE) {
            return;
        }

        var result = shopService.sharpenClaws(game);
        if (result != null) {
            gameMapper.update(game, result);
            printGameStats(game);
        }
    }

    private void buyPotion(Game game) {
        if (game.getLives() >= MAX_LIVES_TO_HAVE) {
            return;
        }

        var result = shopService.buyPotion(game);
        if (result != null) {
            gameMapper.update(game, result);
            printGameStats(game);
        }
    }

    private Game solveMessage(Game game) {
        var message = messageService.getMessageToSolve(game);
        var solveResult = messageService.solveMessage(game, message);

        return gameMapper.update(game, solveResult, message.probability());
    }


    private void printGameStats(Game game) {
        System.out.println("Game " + game.getId() + " result after turn " + game.getTurn() + ": lives=" + game.getLives()
                + ", level=" + game.getLevel() + ", gold=" + game.getGold() + ", score=" + game.getScore());
    }

    private void printResults(Game game) {
        if (game.getScore() >= GAME_SCORE_GOAL) {
            System.out.println("You won the game with score " + game.getScore() + "!");
        } else {
            System.out.println("Game lost. Final score: " + game.getScore());
        }
    }
}
