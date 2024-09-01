package com.brigita.dragons.of.mugloar.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum Probability {

    SURE_THING(1, "Sure thing"),
    PIECE_OF_CAKE(2, "Piece of cake"),
    WALK_IN_THE_PARK(3, "Walk in the park"),
    QUITE_LIKELY(4, "Quite likely"),
    RATHER_DETRIMENTAL(5, "Rather Detrimental"),
    GAMBLE(6, "Gamble"),
    PLAYING_WITH_FIRE(7, "Playing with Fire"),
    RISKY(8, "Risky"),
    SUICIDE_MISSION(9, "Suicide Mission"),
    IMPOSSIBLE(10, "Impossible"),
    HMMM(100, "Hmmm....");

    private final int level;
    private final String description;

    public static List<Probability> getSimpleProbabilities() {
        return List.of(SURE_THING, PIECE_OF_CAKE, WALK_IN_THE_PARK, QUITE_LIKELY, RATHER_DETRIMENTAL);
    }

    public static Probability fromString(String probability) {
        return Arrays.stream(values())
                .filter(value -> value.description.equalsIgnoreCase(probability))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No enum value for text " + probability));
    }
}
