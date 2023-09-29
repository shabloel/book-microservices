package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptDtoChecked;
import com.games.multiplication.domain.model.AttemptChecked;
import com.games.multiplication.domain.dto.AttemptDTO;

import java.util.List;

public interface ChallengeService {


    /**
     * Cheacks whether a userAttempt is correct
     * @param resultAttempt
     * @return ChallengeAttempt
     */
    AttemptChecked verifyAttempt(AttemptDTO resultAttempt);

    List<AttemptDtoChecked> getUserStats(String alias);
}
