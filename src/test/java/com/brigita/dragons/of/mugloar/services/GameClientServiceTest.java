package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.clients.GameClient;
import com.brigita.dragons.of.mugloar.clients.dto.SolveResultDto;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.brigita.dragons.of.mugloar.ValueBuilders.buildGame;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildSolveResultDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameClientServiceTest {

    @InjectMocks
    private GameClientService gameClientService;

    @Mock
    private GameClient client;

    @Test
    void successfullySolveMessage() {
        var game = buildGame(0);
        var messageId = "1";
        var solveResult = buildSolveResultDto(true);

        when(client.solveMessage(game.getId(), messageId)).thenReturn(solveResult);

        var result = gameClientService.solveMessage(game, messageId);

        assertNotNull(result);
        assertEquals(solveResult, result);
    }

    @Test
    void returnResultWhenBadRequestExceptionThrown() {
        var game = buildGame(0);
        var messageId = "1";

        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(400);
        when(client.solveMessage(game.getId(), messageId)).thenThrow(feignException);

        var result = gameClientService.solveMessage(game, messageId);

        assertNotNull(result);
        var expected = new SolveResultDto(false, game);
        assertEquals(expected, result);
    }

    @Test
    void throwExceptionWhenClientReturnsOne() {
        var game = buildGame(0);
        var messageId = "1";

        FeignException feignException = mock(FeignException.class);
        when(feignException.status()).thenReturn(500);
        when(client.solveMessage(game.getId(), messageId)).thenThrow(feignException);

        assertThrows(FeignException.class, () -> gameClientService.solveMessage(game, messageId));
    }
}
