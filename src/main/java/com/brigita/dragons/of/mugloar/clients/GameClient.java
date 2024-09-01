package com.brigita.dragons.of.mugloar.clients;

import com.brigita.dragons.of.mugloar.clients.dto.GameDto;
import com.brigita.dragons.of.mugloar.clients.dto.MessageDto;
import com.brigita.dragons.of.mugloar.clients.dto.ShopItemDto;
import com.brigita.dragons.of.mugloar.clients.dto.ShopResultDto;
import com.brigita.dragons.of.mugloar.clients.dto.SolveResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "dragons-of-mugloar", url = "${client.dragons-of-mugloar.endpoint}")
public interface GameClient {

    @PostMapping("/game/start")
    GameDto startGame();

    @GetMapping("/{gameId}/messages")
    List<MessageDto> getMessages(@PathVariable String gameId);

    @PostMapping("/{gameId}/solve/{adId}")
    SolveResultDto solveMessage(@PathVariable String gameId, @PathVariable String adId);

    @GetMapping("/{gameId}/shop")
    List<ShopItemDto> getShopItems(@PathVariable String gameId);

    @PostMapping("/{gameId}/shop/buy/{itemId}")
    ShopResultDto buyItem(@PathVariable String gameId, @PathVariable String itemId);
}
