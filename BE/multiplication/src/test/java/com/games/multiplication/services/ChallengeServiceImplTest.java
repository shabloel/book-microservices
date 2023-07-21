package com.games.multiplication.services;

import com.games.multiplication.domain.model.ChallengeAttempt;
import com.games.multiplication.domain.dto.ChallengeAttemptDTO;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.repos.ChallengeAttemptRepository;
import com.games.multiplication.repos.UserRepository;
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

    private ChallengeService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new ChallengeServiceImpl(userRepository, challengeAttemptRepository);
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
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
        verify(userRepository).save(new Uzer("Henkie"));
        verify(challengeAttemptRepository).save(challengeAttempt);
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
        verify(userRepository).save(new Uzer("Henkie"));
        verify(challengeAttemptRepository).save(challengeAttempt);
    }

    @Test
    void checkExistingUserTest() {
        //given
        Uzer existingUzer = new Uzer(1L, "Henkie");
        given(userRepository.findByAlias(existingUzer.getAlias())).willReturn(Optional.of(existingUzer));
        ChallengeAttemptDTO challengeAttemptDTO = new ChallengeAttemptDTO(12, 12, "Henkie", 144);


        //when
        ChallengeAttempt result = classUnderTest.verifyAttempt(challengeAttemptDTO);

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
        ChallengeAttempt challengeAttempt1 = new ChallengeAttempt(1L, uzer, 12, 12, 144, false);
        ChallengeAttempt challengeAttempt2 = new ChallengeAttempt(1L, uzer, 12, 12, 144, false);
        List<ChallengeAttempt> listChallengeAttempts = List.of(challengeAttempt1, challengeAttempt2);

        //when
        when(challengeAttemptRepository.findTop10ByUzerAliasOrderByIdDesc(anyString())).thenReturn(listChallengeAttempts);

        //ArrayList<ChallengeAttempt> result = classUnderTest.getLastTenAttemptsForUser("Henkie");

        //then
        verify(challengeAttemptRepository, times(1)).findTop10ByUzerAliasOrderByIdDesc(anyString());
        //then(result.get(0)).isEqualTo(listChallengeAttempts);
    }
}