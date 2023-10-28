package com.games.multiplication.services;

import com.games.multiplication.domain.model.Challenge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ChallengeGeneratorServiceImplTest {

    @Spy
    private Random random;
    private ChallengeGeneratorService challengeGeneratorService;

    @BeforeEach
    public void setup() {
        this.challengeGeneratorService = new ChallengeGeneratorServiceImpl(random);
    }

//    @Test
//    void generateRandomChallengeBetweenExpectedLimits() {
//        given(random.nextInt(88)).willReturn(20, 30);
//
//        Challenge challenge = challengeGeneratorService.randomChallenge();
//
//        then(challenge).isEqualTo(new Challenge(31, 41));
//    }
}
