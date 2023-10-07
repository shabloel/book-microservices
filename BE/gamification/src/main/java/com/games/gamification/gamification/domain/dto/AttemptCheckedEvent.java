package com.games.gamification.gamification.domain.dto;

import lombok.Value;

@Value
public class AttemptCheckedEvent {

    private long id;
    private boolean correct;
    private int factorA;
    private int factorB;
    private long userId;
    private String userAlias;
    private long userGuess;
}
