package com.games.multiplication.domain.dto;

import lombok.Value;

@Value
public class AttemptCheckedDto {
    private long id;
    private boolean correct;
    private int factorA;
    private int factorB;
    private long userId;
    private String userAlias;
}