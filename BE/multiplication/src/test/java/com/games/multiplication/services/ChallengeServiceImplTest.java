package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptCheckedEvent;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.AttemptChecked;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.events.ChallengeEventPub;
import com.games.multiplication.repos.AttemptRepository;
import com.games.multiplication.repos.UserRepository;
import com.games.multiplication.services.mapper.SourceDestinationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceImplTest {

    @Mock
    private AttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChallengeEventPub challengeEventPub;

    @Mock
    private SourceDestinationMapper sourceDestinationMapper;

    private ChallengeService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new ChallengeServiceImpl(userRepository, challengeEventPub, attemptRepository, sourceDestinationMapper);
    }

    @Test
    void verifyCorrectAttempt() {

        //given
        Uzer existingUzer = new Uzer(1L, "Henkie");
        AttemptDTO attemptDTO = new AttemptDTO(12, 12, "Henkie", 144);
        AttemptChecked attemptChecked = new AttemptChecked(1L, existingUzer, 12, 12, 144, true);
        AttemptCheckedEvent attemptCheckedEvent = new AttemptCheckedEvent(1L, 12, 12, 1L, "Henkie", true, 144);
        given(attemptRepository.save(any())).willReturn(attemptChecked);
        given(sourceDestinationMapper.attemptDtoToAttemptChecked(any())).willReturn(attemptChecked);
        //when
        AttemptChecked result = classUnderTest.verifyAttempt(attemptDTO);

        //then
        then(result.isCorrect()).isTrue();
        verify(userRepository).save(new Uzer("Henkie"));
        verify(attemptRepository).save(any());
        verify(challengeEventPub).challengeSolved(attemptChecked);
    }

    @Test
    void verifyWrongAttempt() {

        //given
        Uzer existingUzer = new Uzer(1L, "Henkie");
        AttemptDTO attemptDTO =
                new AttemptDTO(12, 12, "Henkie", 112);
        AttemptChecked attemptChecked = new AttemptChecked(1L, existingUzer, 12, 12, 144, true);

        given(sourceDestinationMapper.attemptDtoToAttemptChecked(any())).willReturn(attemptChecked);
        given(attemptRepository.save(any())).willReturn(attemptChecked);

        //when
        AttemptChecked result = classUnderTest.verifyAttempt(attemptDTO);

        //then
        then(result.isCorrect()).isFalse();
        verify(userRepository).save(new Uzer("Henkie"));
        verify(attemptRepository).save(any());
    }

    @Test
    void checkExistingUserTest() {
        //given
        Uzer existingUzer = new Uzer(1L, "Henkie");
        AttemptChecked attemptChecked = new AttemptChecked(1L, existingUzer, 12, 12, 144, true);
        given(attemptRepository.save(any())).willReturn(attemptChecked);
        given(sourceDestinationMapper.attemptDtoToAttemptChecked(any())).willReturn(attemptChecked);
        given(userRepository.findByAlias("Henkie")).willReturn(Optional.of(existingUzer));
        AttemptDTO attemptDTO = new AttemptDTO(12, 12, "Henkie", 144);


        //when
        AttemptChecked result = classUnderTest.verifyAttempt(attemptDTO);

        //then
        then(result.isCorrect()).isTrue();
        then(result.getUzer()).isEqualTo(existingUzer);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(any());
        verify(challengeEventPub).challengeSolved(any());
    }

    @Test
    void getLatestTenAttempts() {
        //given
        Uzer uzer = new Uzer(1L, "Henkie");
        AttemptChecked attemptChecked1 = new AttemptChecked(1L, null, 12, 12, 12, false);
        AttemptChecked attemptChecked2 = new AttemptChecked(1L, null, 12, 12, 144, true);
        List<AttemptChecked> listAttemptChecked = List.of(attemptChecked1, attemptChecked2);

        AttemptCheckedEvent attemptCheckedEvent1 = new AttemptCheckedEvent(1L, 12, 12, 1L, "Henkie", false, 12);
        AttemptCheckedEvent attemptCheckedEvent2 = new AttemptCheckedEvent(1L, 12, 12, 2L, "Kees", true, 144);
        List<AttemptCheckedEvent> listAttemptCheckedEvent = List.of(attemptCheckedEvent1, attemptCheckedEvent2);
        given(attemptRepository.findTop10ByUzerAliasOrderByIdDesc("Henkie")).willReturn(listAttemptChecked);
        given(sourceDestinationMapper.attemptsCheckedToAttemptsCheckedEvent(any())).willReturn(listAttemptCheckedEvent);
        //when
        List<AttemptCheckedEvent> result = classUnderTest.getUserStats("Henkie");

        //then
        then(result).isEqualTo(listAttemptCheckedEvent);
    }
}