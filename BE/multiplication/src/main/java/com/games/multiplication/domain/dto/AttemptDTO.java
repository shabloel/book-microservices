package com.games.multiplication.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class AttemptDTO {

    @Min(1)
    int factorA;
    @Max(99)
    int factorB;
    @NotBlank
    String userAlias;
    @Positive
    int guess;
}
