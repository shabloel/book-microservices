package com.games.multiplication.services;

import com.games.multiplication.domain.model.ChallengeAttempt;
import com.games.multiplication.domain.model.ChallengeAttemptDTO;

public interface ChallengeService {


    /**
     * Cheacks whether a userAttempt is correct
     * @param resultAttempt
     * @return ChallengeAttempt
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);
}
