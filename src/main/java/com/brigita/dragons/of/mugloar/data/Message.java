package com.brigita.dragons.of.mugloar.data;

public record Message(
        String id,
        int reward,
        Probability probability,
        int expiresIn
) {
}
