package com.games.gamification.gamification.domain.dto;

import lombok.Value;

@Value
public class ChallengeSolvedDto {

    private long attemptId;
    private boolean correct;
    private int factorA;
    private int factorB;
    private long userId;
    private String userAlias;
}
