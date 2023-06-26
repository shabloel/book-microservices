package com.games.multiplication.services;

import com.games.multiplication.challenges.ChallengeAttempt;
import com.games.multiplication.challenges.ChallengeAttemptDTO;

public interface ChallengeService {


    /**
     * Cheacks whether a userAttempt is correct
     * @param resultAttempt
     * @return ChallengeAttempt
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);
}