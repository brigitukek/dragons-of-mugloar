package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.clients.dto.ShopResultDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static com.brigita.dragons.of.mugloar.ValueBuilders.buildGame;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildShopItemDto;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildShopResultDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @Mock
    private GameClientService gameClientService;

    @InjectMocks
    private ShopService shopService;

    @Test
    void successfullySharpenClaws() {
        var game = buildGame(120);
        var shopItem = buildShopItemDto("Claw Sharpening");
        ShopResultDto shopResult = buildShopResultDto();

        when(gameClientService.getShopItems(game.getId())).thenReturn(List.of(shopItem));
        when(gameClientService.buyItem(game.getId(), shopItem.id())).thenReturn(shopResult);

        var result = shopService.sharpenClaws(game);

        assertNotNull(result);
        assertEquals(shopResult, result);
        verify(gameClientService).buyItem(game.getId(), shopItem.id());
    }

    @Test
    void notSharpenedClawsWhenGoldNotEnough() {
        var game = buildGame(10);
        var shopItem = buildShopItemDto("Claw Sharpening");

        when(gameClientService.getShopItems(game.getId())).thenReturn(List.of(shopItem));

        var result = shopService.sharpenClaws(game);

        assertNull(result);
        verify(gameClientService, never()).buyItem(game.getId(), shopItem.id());
    }

    @Test
    void successfullyBuyPotion() {
        var game = buildGame(120);
        var shopItem = buildShopItemDto("healing potion");
        ShopResultDto shopResult = buildShopResultDto();

        when(gameClientService.getShopItems(game.getId())).thenReturn(List.of(shopItem));
        when(gameClientService.buyItem(game.getId(), shopItem.id())).thenReturn(shopResult);

        var result = shopService.buyPotion(game);

        assertNotNull(result);
        assertEquals(shopResult, result);
        verify(gameClientService).buyItem(game.getId(), shopItem.id());
    }

    @Test
    void dontBuyPotionWhenItIsNotFound() {
        var game = buildGame(120);
        var shopItem = buildShopItemDto("potion");

        when(gameClientService.getShopItems(game.getId())).thenReturn(List.of(shopItem));

        assertThrows(NoSuchElementException.class, () -> shopService.buyPotion(game));

        verify(gameClientService, never()).buyItem(game.getId(), shopItem.id());
    }
}
