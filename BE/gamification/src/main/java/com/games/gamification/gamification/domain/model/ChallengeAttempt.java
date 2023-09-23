package com.games.gamification.gamification.domain.model;

import lombok.Value;

@Value
public class ChallengeAttempt {

    private long id;
    private boolean correct;
    private int factorA;
    private int factorB;
    private long userId;
    private String userAlias;
}
