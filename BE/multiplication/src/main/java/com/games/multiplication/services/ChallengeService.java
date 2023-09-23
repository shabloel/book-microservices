package com.games.multiplication.services;

import com.games.multiplication.domain.model.Attempt;
import com.games.multiplication.domain.dto.AttemptDTO;

import java.util.List;

public interface ChallengeService {


    /**
     * Cheacks whether a userAttempt is correct
     * @param resultAttempt
     * @return ChallengeAttempt
     */
    Attempt verifyAttempt(AttemptDTO resultAttempt);

    List<Attempt> getUserStats(String alias);
}
