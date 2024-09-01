package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.mappers.GameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.brigita.dragons.of.mugloar.ValueBuilders.buildGame;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildGameDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameClientService gameClientService;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameService gameService;

    @Test
    void successfullySelectSimpleMessage() {
        var gameDto = buildGameDto();
        var game = buildGame(0);

        when(gameClientService.startGame()).thenReturn(gameDto);
        when(gameMapper.mapFromDto(gameDto)).thenReturn(game);

        var result = gameService.startGame();

        assertNotNull(result);
        assertEquals(game, result);
    }
}
