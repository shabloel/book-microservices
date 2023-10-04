package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptCheckedEvent;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.AttemptChecked;

import java.util.List;

public interface ChallengeService {


    /**
     * Cheacks whether a userAttempt is correct
     *
     * @param resultAttempt
     * @return ChallengeAttempt
     */
    AttemptChecked verifyAttempt(AttemptDTO resultAttempt);

    List<AttemptCheckedEvent> getUserStats(String alias);
}
