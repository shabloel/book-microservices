package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptCheckedDto;
import com.games.multiplication.domain.model.Attempt;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.repos.ChallengeAttemptRepository;
import com.games.multiplication.repos.UserRepository;
import com.games.multiplication.serviceclient.GamificationServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceImplTest {

    @Mock
    private ChallengeAttemptRepository challengeAttemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GamificationServiceClient gamificationServiceClient;

    private ChallengeService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new ChallengeServiceImpl(userRepository, gamificationServiceClient, challengeAttemptRepository);
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
    }

    @Test
    void verifyCorrectAttempt() {

        //given
        AttemptDTO attemptDTO =
                new AttemptDTO(12, 12, "Henkie", 144);

        //when
        Attempt attempt = classUnderTest.verifyAttempt(attemptDTO);

        AttemptCheckedDto attemptCheckedDto = new AttemptCheckedDto(
                attempt.getId(),
                attempt.isCorrect(),
                attempt.getFactorA(),
                attempt.getFactorB(),
                attempt.getUzer().getId(),
                attempt.getUzer().getAlias()
        );

        //then
        then(attempt.isCorrect()).isTrue();
        verify(userRepository).save(new Uzer("Henkie"));
        verify(challengeAttemptRepository).save(attempt);
        verify(gamificationServiceClient).sendAttempt(attemptCheckedDto);
    }

    @Test
    void verifyWrongAttempt() {

        //given
        AttemptDTO attemptDTO =
                new AttemptDTO(12, 12, "Henkie", 112);

        //when
        Attempt attempt = classUnderTest.verifyAttempt(attemptDTO);

        //then
        then(attempt.isCorrect()).isFalse();
        verify(userRepository).save(new Uzer("Henkie"));
        verify(challengeAttemptRepository).save(attempt);
    }

    @Test
    void checkExistingUserTest() {
        //given
        Uzer existingUzer = new Uzer(1L, "Henkie");
        given(userRepository.findByAlias(existingUzer.getAlias())).willReturn(Optional.of(existingUzer));
        AttemptDTO attemptDTO = new AttemptDTO(12, 12, "Henkie", 144);


        //when
        Attempt result = classUnderTest.verifyAttempt(attemptDTO);

        //then
        then(result.isCorrect()).isTrue();
        verify(userRepository, never()).save(any());
        verify(challengeAttemptRepository).save(result);
    }

    @Test
    @Disabled
    void getLatestTenAttempts() {
        //given
        Uzer uzer = new Uzer(1L, "Henkie");
        Attempt attempt1 = new Attempt(1L, uzer, 12, 12, 144, false);
        Attempt attempt2 = new Attempt(1L, uzer, 12, 12, 144, false);
        List<Attempt> listAttempts = List.of(attempt1, attempt2);

        //when
        when(challengeAttemptRepository.findTop10ByUzerAliasOrderByIdDesc(anyString())).thenReturn(listAttempts);

        //ArrayList<ChallengeAttempt> result = classUnderTest.getLastTenAttemptsForUser("Henkie");

        //then
        verify(challengeAttemptRepository, times(1)).findTop10ByUzerAliasOrderByIdDesc(anyString());
        //then(result.get(0)).isEqualTo(listChallengeAttempts);
    }
}