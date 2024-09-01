package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.clients.dto.ShopItemDto;
import com.brigita.dragons.of.mugloar.clients.dto.ShopResultDto;
import com.brigita.dragons.of.mugloar.data.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private static final String CLAW_SHARPENING = "claw sharpening";
    private static final String HEALING_POTION = "healing potion";

    @Autowired
    private GameClientService gameClientService;

    public ShopResultDto sharpenClaws(Game game) {
        return buyItem(game, CLAW_SHARPENING);
    }

    public ShopResultDto buyPotion(Game game) {
        return buyItem(game, HEALING_POTION);
    }

    private ShopItemDto getItem(String gameId, String itemName) {
        List<ShopItemDto> shopItems = gameClientService.getShopItems(gameId);
        return shopItems.stream()
                .filter(item -> item.name().toLowerCase().contains(itemName))
                .findFirst()
                .orElseThrow();
    }

    private ShopResultDto buyItem(Game game, String itemName) {
        var item = getItem(game.getId(), itemName);
        if (game.getGold() >= item.cost()) {
            return gameClientService.buyItem(game.getId(), item.id());
        }
        return null;
    }
}
