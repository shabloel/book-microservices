package com.games.multiplication.services;

import com.games.multiplication.domain.model.ChallengeAttempt;
import com.games.multiplication.domain.dto.ChallengeAttemptDTO;

import java.util.List;

public interface ChallengeService {


    /**
     * Cheacks whether a userAttempt is correct
     * @param resultAttempt
     * @return ChallengeAttempt
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);

    List<ChallengeAttempt> getUserStats(String alias);
}
