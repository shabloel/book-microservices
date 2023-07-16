package com.games.multiplication.services;

import com.games.multiplication.domain.model.ChallengeAttempt;
import com.games.multiplication.domain.model.ChallengeAttemptDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class ChallengeServiceImplTest {

    ChallengeService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new ChallengeServiceImpl();
    }

    @Test
    void verifyCorrectAttempt() {

        //given
        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(12, 12, "Henkie", 144);

        //when
        ChallengeAttempt challengeAttempt = classUnderTest.verifyAttempt(challengeAttemptDTO);

        //then
        then(challengeAttempt.isCorrect()).isTrue();
    }

    @Test
    void verifyWrongAttempt() {

        //given
        ChallengeAttemptDTO challengeAttemptDTO =
                new ChallengeAttemptDTO(12, 12, "Henkie", 112);

        //when
        ChallengeAttempt challengeAttempt = classUnderTest.verifyAttempt(challengeAttemptDTO);

        //then
        then(challengeAttempt.isCorrect()).isFalse();
    }
}