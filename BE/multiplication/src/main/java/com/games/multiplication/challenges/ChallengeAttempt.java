package com.games.multiplication.challenges;

import com.games.multiplication.users.User;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ChallengeAttempt {
    private Long id;
    private User user;
    private int factorA;
    private int factorB;
    private int userGuess;
    private boolean correct;
}
