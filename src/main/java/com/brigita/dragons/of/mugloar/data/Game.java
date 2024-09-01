package com.brigita.dragons.of.mugloar.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public class Game {

    private String id;
    @Setter
    private int lives;
    @Setter
    private int level;
    @Setter
    private int gold;
    @Setter
    private int score;
    @Setter
    private int turn;
    @Setter
    private Probability lastProbability;
}
