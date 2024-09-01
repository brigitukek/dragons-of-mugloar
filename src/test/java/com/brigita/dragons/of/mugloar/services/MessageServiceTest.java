package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.data.Probability;
import com.brigita.dragons.of.mugloar.mappers.MessageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.brigita.dragons.of.mugloar.ValueBuilders.buildGame;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildMessage;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildMessageDto;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildSolveResultDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private GameClientService gameClientService;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageService messageService;

    @Test
    void successfullySelectSimpleMessage() {
        var game = buildGame(Probability.PIECE_OF_CAKE, 3);
        var messageDtos = List.of(
                buildMessageDto(Probability.PIECE_OF_CAKE.getDescription()),
                buildMessageDto(Probability.GAMBLE.getDescription()),
                buildMessageDto(Probability.SURE_THING.getDescription()),
                buildMessageDto(Probability.WALK_IN_THE_PARK.getDescription())
        );
        var messages = List.of(
                buildMessage(Probability.PIECE_OF_CAKE),
                buildMessage(Probability.GAMBLE),
                buildMessage(Probability.SURE_THING),
                buildMessage(Probability.WALK_IN_THE_PARK)
        );

        when(gameClientService.getMessages(game.getId())).thenReturn(messageDtos);
        when(messageMapper.mapFromDto(messageDtos)).thenReturn(messages);

        var result = messageService.getMessageToSolve(game);

        assertNotNull(result);
        assertEquals(Probability.SURE_THING, result.probability());
    }

    @Test
    void successfullySelectOptimalMessage() {
        var game = buildGame(Probability.PIECE_OF_CAKE, 3);
        var messageDtos = List.of(
                buildMessageDto(Probability.PLAYING_WITH_FIRE.getDescription()),
                buildMessageDto(Probability.PIECE_OF_CAKE.getDescription()),
                buildMessageDto(Probability.HMMM.getDescription()),
                buildMessageDto(Probability.IMPOSSIBLE.getDescription())
        );
        var messages = List.of(
                buildMessage(Probability.PLAYING_WITH_FIRE),
                buildMessage(Probability.PIECE_OF_CAKE),
                buildMessage(Probability.HMMM),
                buildMessage(Probability.IMPOSSIBLE)
        );

        when(gameClientService.getMessages(game.getId())).thenReturn(messageDtos);
        when(messageMapper.mapFromDto(messageDtos)).thenReturn(messages);

        var result = messageService.getMessageToSolve(game);

        assertNotNull(result);
        assertEquals(Probability.PLAYING_WITH_FIRE, result.probability());
    }

    @Test
    void successfullySelectSimplestMessageWhen1LiveLeft() {
        var game = buildGame(Probability.PIECE_OF_CAKE, 1);
        var messageDtos = List.of(
                buildMessageDto(Probability.PLAYING_WITH_FIRE.getDescription()),
                buildMessageDto(Probability.PIECE_OF_CAKE.getDescription()),
                buildMessageDto(Probability.HMMM.getDescription()),
                buildMessageDto(Probability.IMPOSSIBLE.getDescription())
        );
        var messages = List.of(
                buildMessage(Probability.PLAYING_WITH_FIRE),
                buildMessage(Probability.PIECE_OF_CAKE),
                buildMessage(Probability.HMMM),
                buildMessage(Probability.IMPOSSIBLE)
        );

        when(gameClientService.getMessages(game.getId())).thenReturn(messageDtos);
        when(messageMapper.mapFromDto(messageDtos)).thenReturn(messages);

        var result = messageService.getMessageToSolve(game);

        assertNotNull(result);
        assertEquals(Probability.PIECE_OF_CAKE, result.probability());
    }

    @Test
    void exceptionWhenNoMessagesReturned() {
        var game = buildGame(Probability.PIECE_OF_CAKE, 1);

        when(gameClientService.getMessages(game.getId())).thenReturn(List.of());
        when(messageMapper.mapFromDto(any())).thenReturn(List.of());

        assertThrows(IllegalStateException.class, () -> messageService.getMessageToSolve(game));
    }

    @Test
    void successfullySolveMessage() {
        var game = buildGame(Probability.PIECE_OF_CAKE, 3);
        var message = buildMessage(Probability.PIECE_OF_CAKE);
        var solveResult = buildSolveResultDto(true);

        when(gameClientService.solveMessage(game, message.id())).thenReturn(solveResult);

        var result = messageService.solveMessage(game, message);

        assertNotNull(result);
        assertEquals(solveResult, result);
        verify(gameClientService).solveMessage(game, message.id());
    }
}
