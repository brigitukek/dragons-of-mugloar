package com.brigita.dragons.of.mugloar.mappers;

import com.brigita.dragons.of.mugloar.data.Probability;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.brigita.dragons.of.mugloar.ValueBuilders.buildMessage;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildMessageDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {

    @InjectMocks
    private MessageMapper messageMapper;

    @Test
    void mapFromDtoWhenListIsEmpty() {
        var result = messageMapper.mapFromDto(List.of());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void mapFromDtoWhenEncodeNull() {
        var messageDto = buildMessageDto(null, "Gamble", "messageId");
        var message = buildMessage(Probability.GAMBLE);

        var result = messageMapper.mapFromDto(List.of(messageDto));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(message, result.getFirst());
    }

    @Test
    void mapFromDtoWhenEncode1() {
        var messageDto = buildMessageDto(1, "R2FtYmxl", "bWVzc2FnZUlk");
        var message = buildMessage(Probability.GAMBLE);

        var result = messageMapper.mapFromDto(List.of(messageDto));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(message, result.getFirst());
    }

    @Test
    void mapFromDtoWhenEncode2() {
        var messageDto = buildMessageDto(2, "Tnzoyr", "zrffntrVq");
        var message = buildMessage(Probability.GAMBLE);

        var result = messageMapper.mapFromDto(List.of(messageDto));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(message, result.getFirst());
    }
}
