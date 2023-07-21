package com.games.multiplication.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChallengeAttempt {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Uzer uzer;
    private int factorA;
    private int factorB;
    private int userGuess;
    private boolean correct;
}
