package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.clients.GameClient;
import com.brigita.dragons.of.mugloar.clients.dto.GameDto;
import com.brigita.dragons.of.mugloar.clients.dto.MessageDto;
import com.brigita.dragons.of.mugloar.clients.dto.ShopItemDto;
import com.brigita.dragons.of.mugloar.clients.dto.ShopResultDto;
import com.brigita.dragons.of.mugloar.clients.dto.SolveResultDto;
import com.brigita.dragons.of.mugloar.data.Game;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameClientService {

    @Autowired
    private GameClient client;

    public GameDto startGame() {
        return client.startGame();
    }

    public SolveResultDto solveMessage(Game game, String messageId) {
        try {
            return client.solveMessage(game.getId(), messageId);
        } catch (FeignException exception) {
            if (exception.status() == HttpStatus.BAD_REQUEST.value()) {
                return new SolveResultDto(false, game);
            }
            throw exception;
        }
    }

    public List<MessageDto> getMessages(String gameId) {
        return client.getMessages(gameId);
    }

    public List<ShopItemDto> getShopItems(String gameId) {
        return client.getShopItems(gameId);
    }

    public ShopResultDto buyItem(String gameId, String itemId) {
        return client.buyItem(gameId, itemId);
    }
}
