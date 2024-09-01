package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.data.Game;
import com.brigita.dragons.of.mugloar.mappers.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameClientService gameClientService;
    @Autowired
    private GameMapper gameMapper;

    public Game startGame() {
        var gameDto = gameClientService.startGame();
        return gameMapper.mapFromDto(gameDto);
    }
}
