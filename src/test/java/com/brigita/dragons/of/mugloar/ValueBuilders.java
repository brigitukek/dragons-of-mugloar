package com.brigita.dragons.of.mugloar;

import com.brigita.dragons.of.mugloar.clients.dto.GameDto;
import com.brigita.dragons.of.mugloar.clients.dto.MessageDto;
import com.brigita.dragons.of.mugloar.clients.dto.ShopItemDto;
import com.brigita.dragons.of.mugloar.clients.dto.ShopResultDto;
import com.brigita.dragons.of.mugloar.clients.dto.SolveResultDto;
import com.brigita.dragons.of.mugloar.data.Game;
import com.brigita.dragons.of.mugloar.data.Message;
import com.brigita.dragons.of.mugloar.data.Probability;

public class ValueBuilders {

    public static GameDto buildGameDto() {
        return new GameDto("gameId", 3, 0, 0, 0, 0);
    }

    public static Game buildGame(Probability probability, int lives) {
        return new Game("gameId", lives, 0, 0, 0, 0, probability);
    }

    public static Game buildGame(int gold) {
        return new Game("gameId", 3, 0, gold, 0, 0, null);
    }

    public static Game buildGame(int gold, int lives, int level, int score, int turn, Probability probability) {
        return new Game("gameId", lives, level, gold, score, turn, probability);
    }

    public static Game buildGame(int score, int lives) {
        return new Game("gameId", lives, 0, 30, score, 0, null);
    }

    public static ShopItemDto buildShopItemDto(String name) {
        return new ShopItemDto(name + " id", name, 30);
    }

    public static ShopResultDto buildShopResultDto() {
        return new ShopResultDto(true, 90, 3, 1, 1);
    }

    public static MessageDto buildMessageDto(String probability) {
        return new MessageDto("messageId", "Task 1", 32, null, probability, 3);
    }

    public static MessageDto buildMessageDto(Integer encrypted, String probability, String id) {
        return new MessageDto(id, "Task 1", 32, encrypted, probability, 3);
    }

    public static Message buildMessage(Probability probability) {
        return new Message("messageId", 32, probability, 3);
    }

    public static SolveResultDto buildSolveResultDto(boolean success) {
        return new SolveResultDto(success, 3, 90, 90, 1, "Success!");
    }
}
