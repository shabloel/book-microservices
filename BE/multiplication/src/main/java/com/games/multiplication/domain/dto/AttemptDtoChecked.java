package com.games.multiplication.domain.dto;

import lombok.Value;

@Value
public class AttemptDtoChecked {
    private Long id;
    private int factorA;
    private int factorB;
    private Long userId;
    private String userAlias;
    private boolean correct;
}
