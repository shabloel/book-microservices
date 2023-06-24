package com.games.multiplication.services;

import com.games.multiplication.challenges.ChallengeAttempt;
import com.games.multiplication.challenges.ChallengeAttemptDTO;
import com.games.multiplication.users.User;
import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO challengeAttemptDto) {

        boolean isCorrect =
                challengeAttemptDto.getFactorA() * challengeAttemptDto.getFactorB() == challengeAttemptDto.getGuess() ? true : false;

        User user = new User(null, challengeAttemptDto.getUserAlias());

        ChallengeAttempt challengeAttempt =
                new ChallengeAttempt(null,
                        user,
                        challengeAttemptDto.getFactorA(),
                        challengeAttemptDto.getFactorB(),
                        challengeAttemptDto.getGuess(),
                        isCorrect);
        return challengeAttempt;
    }
}
