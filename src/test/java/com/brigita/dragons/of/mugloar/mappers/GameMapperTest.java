package com.brigita.dragons.of.mugloar.mappers;

import com.brigita.dragons.of.mugloar.data.Probability;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.brigita.dragons.of.mugloar.ValueBuilders.buildGame;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildGameDto;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildShopResultDto;
import static com.brigita.dragons.of.mugloar.ValueBuilders.buildSolveResultDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GameMapperTest {

    @InjectMocks
    private GameMapper gameMapper;

    @Test
    void testMapFromDto() {
        var gameDto = buildGameDto();
        var game = buildGame(0);

        var result = gameMapper.mapFromDto(gameDto);

        assertNotNull(result);
        assertEquals(game, result);
    }

    @Test
    void testUpdateAfterShopping() {
        var game = buildGame(222);
        var shopResult = buildShopResultDto();

        var result = gameMapper.update(game, shopResult);

        assertNotNull(result);
        var expected = buildGame(90, 3, 1, 0, 1, null);
        assertEquals(expected, result);
    }

    @Test
    void testUpdateAfterSolving() {
        var game = buildGame(40);
        var solveResult = buildSolveResultDto(true);

        var result = gameMapper.update(game, solveResult, Probability.PLAYING_WITH_FIRE);

        assertNotNull(result);
        var expected = buildGame(90, 3, 0, 90, 1, Probability.PLAYING_WITH_FIRE);
        assertEquals(expected, result);
    }
}
