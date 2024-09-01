package com.brigita.dragons.of.mugloar.mappers;

import com.brigita.dragons.of.mugloar.clients.dto.MessageDto;
import com.brigita.dragons.of.mugloar.data.Message;
import com.brigita.dragons.of.mugloar.data.Probability;
import com.brigita.dragons.of.mugloar.utils.DecodingUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper {

    public List<Message> mapFromDto(List<MessageDto> messageDtos) {
        return messageDtos.stream()
                .map(this::mapMessage)
                .toList();
    }

    private Message mapMessage(MessageDto messageDto) {
        var probability = resolveProbability(messageDto.probability(), messageDto.encrypted());
        var adId = DecodingUtils.decode(messageDto.adId(), messageDto.encrypted());
        return new Message(adId, messageDto.reward(), probability, messageDto.expiresIn());
    }

    private Probability resolveProbability(String probability, Integer encrypted) {
        var decoded = DecodingUtils.decode(probability, encrypted);
        return Probability.fromString(decoded);
    }
}
