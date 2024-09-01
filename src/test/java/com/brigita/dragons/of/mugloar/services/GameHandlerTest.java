package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.data.Probability;
import com.brigita.dragons.of.mugloar.mappers.GameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.brigita.dragons.of.mugloar.ValueBuilders.buildGame;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildMessage;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildShopResultDto;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildSolveResultDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameHandlerTest {

    @Mock
    private GameService gameService;

    @Mock
    private MessageService messageService;

    @Mock
    private ShopService shopService;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameHandler gameHandler;

    @Test
    void successfullyPlayGame() {
        var game = buildGame(0, 3);
        var message = buildMessage(Probability.PIECE_OF_CAKE);
        var solveResult = buildSolveResultDto(true);

        when(gameService.startGame()).thenReturn(game);
        when(messageService.getMessageToSolve(game)).thenReturn(message);
        when(messageService.solveMessage(game, message)).thenReturn(solveResult);
        when(gameMapper.update(game, solveResult, message.probability())).thenReturn(buildGame(1001, 3));

        gameHandler.playGame();

        verify(gameMapper).update(game, solveResult, message.probability());
        verify(gameMapper, never()).update(any(), any());
        verify(shopService, never()).buyPotion(game);
        verify(shopService, never()).sharpenClaws(game);
    }

    @Test
    void failPlayGame() {
        var game = buildGame(55, 1);
        var message = buildMessage(Probability.PIECE_OF_CAKE);
        var solveResult = buildSolveResultDto(false);

        when(gameService.startGame()).thenReturn(game);
        when(messageService.getMessageToSolve(game)).thenReturn(message);
        when(messageService.solveMessage(game, message)).thenReturn(solveResult);
        when(gameMapper.update(game, solveResult, message.probability())).thenReturn(buildGame(55, 0));

        gameHandler.playGame();

        verify(gameMapper).update(game, solveResult, message.probability());
        verify(gameMapper, never()).update(any(), any());
        verify(shopService, never()).sharpenClaws(game);
    }

    @Test
    void successfullyPlayGameAndSharpenClaws() {
        var game = buildGame(244);
        var message = buildMessage(Probability.PIECE_OF_CAKE);
        var solveResult = buildSolveResultDto(true);
        var shopResult = buildShopResultDto();

        when(gameService.startGame()).thenReturn(game);
        when(shopService.sharpenClaws(game)).thenReturn(shopResult);
        when(gameMapper.update(game, shopResult)).thenReturn(game);
        when(messageService.getMessageToSolve(game)).thenReturn(message);
        when(messageService.solveMessage(game, message)).thenReturn(solveResult);
        when(gameMapper.update(game, solveResult, message.probability())).thenReturn(buildGame(1001, 3));

        gameHandler.playGame();

        verify(gameMapper).update(game, solveResult, message.probability());
        verify(gameMapper).update(game, shopResult);
        verify(shopService, never()).buyPotion(game);
    }

    @Test
    void successfullyPlayGameAndButNotSharpenClaws() {
        var game = buildGame(244);
        var message = buildMessage(Probability.PIECE_OF_CAKE);
        var solveResult = buildSolveResultDto(true);

        when(gameService.startGame()).thenReturn(game);
        when(shopService.sharpenClaws(game)).thenReturn(null);
        when(messageService.getMessageToSolve(game)).thenReturn(message);
        when(messageService.solveMessage(game, message)).thenReturn(solveResult);
        when(gameMapper.update(game, solveResult, message.probability())).thenReturn(buildGame(1001, 3));

        gameHandler.playGame();

        verify(gameMapper).update(game, solveResult, message.probability());
        verify(gameMapper, never()).update(any(), any());
        verify(shopService, never()).buyPotion(game);
    }

    @Test
    void successfullyPlayGameAndBuyPotion() {
        var game = buildGame(20, 2);
        var message = buildMessage(Probability.PIECE_OF_CAKE);
        var solveResult = buildSolveResultDto(true);
        var shopResult = buildShopResultDto();

        when(gameService.startGame()).thenReturn(game);
        when(shopService.buyPotion(game)).thenReturn(shopResult);
        when(gameMapper.update(game, shopResult)).thenReturn(game);
        when(messageService.getMessageToSolve(game)).thenReturn(message);
        when(messageService.solveMessage(game, message)).thenReturn(solveResult);
        when(gameMapper.update(game, solveResult, message.probability())).thenReturn(buildGame(1001, 3));

        gameHandler.playGame();

        verify(gameMapper).update(game, solveResult, message.probability());
        verify(gameMapper).update(game, shopResult);
        verify(shopService, never()).sharpenClaws(game);
    }

    @Test
    void successfullyPlayGameAndButNotBuyPotion() {
        var game = buildGame(20, 2);
        var message = buildMessage(Probability.PIECE_OF_CAKE);
        var solveResult = buildSolveResultDto(true);

        when(gameService.startGame()).thenReturn(game);
        when(shopService.buyPotion(game)).thenReturn(null);
        when(messageService.getMessageToSolve(game)).thenReturn(message);
        when(messageService.solveMessage(game, message)).thenReturn(solveResult);
        when(gameMapper.update(game, solveResult, message.probability())).thenReturn(buildGame(1001, 3));

        gameHandler.playGame();

        verify(gameMapper).update(game, solveResult, message.probability());
        verify(gameMapper, never()).update(any(), any());
        verify(shopService, never()).sharpenClaws(game);
    }
}
