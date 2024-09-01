package com.brigita.dragons.of.mugloar.services;

import com.brigita.dragons.of.mugloar.clients.dto.SolveResultDto;
import com.brigita.dragons.of.mugloar.data.Game;
import com.brigita.dragons.of.mugloar.data.Message;
import com.brigita.dragons.of.mugloar.data.Probability;
import com.brigita.dragons.of.mugloar.mappers.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private GameClientService gameClientService;
    @Autowired
    private MessageMapper messageMapper;

    public SolveResultDto solveMessage(Game game, Message message) {
        return gameClientService.solveMessage(game, message.id());
    }

    public Message getMessageToSolve(Game game) {
        var messages = getMessages(game.getId());
        return selectMessage(messages, game);
    }

    private List<Message> getMessages(String gameId) {
        var messages = gameClientService.getMessages(gameId);
        return messageMapper.mapFromDto(messages);
    }

    private Message selectMessage(List<Message> messages, Game game) {
        if (messages.isEmpty()) {
            throw new IllegalStateException("No messages to solve");
        }

        if (game.getLives() == 1) {
            return getMinProbabilityMessage(messages);
        }

        var filteredMessages = messages.stream()
                .filter(message -> game.getLastProbability() != message.probability()).toList();
        var simpleMessage = getSimpleMessage(filteredMessages);
        return simpleMessage != null ? simpleMessage : getMinProbabilityMessage(filteredMessages);
    }

    private Message getSimpleMessage(List<Message> messages) {
        return messages.stream()
                .filter(message -> Probability.getSimpleProbabilities().contains(message.probability()))
                .max(Comparator.comparingInt(Message::reward)
                        .thenComparingInt(Message::expiresIn))
                .orElse(null);
    }

    private Message getMinProbabilityMessage(List<Message> messages) {
        return messages.stream()
                .min(Comparator.comparingInt(message -> message.probability().getLevel()))
                .orElseThrow();
    }
}
