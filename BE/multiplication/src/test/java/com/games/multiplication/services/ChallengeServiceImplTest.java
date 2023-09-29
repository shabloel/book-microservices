package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptDtoChecked;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.AttemptChecked;
import com.games.multiplication.domain.model.Uzer;
import com.games.multiplication.repos.AttemptRepository;
import com.games.multiplication.repos.UserRepository;
import com.games.multiplication.serviceclient.GamificationServiceClient;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceImplTest {

    @Mock
    private AttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GamificationServiceClient gamificationServiceClient;

    @Mock
    private SourceDestinationMapper sourceDestinationMapper;

    private ChallengeService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new ChallengeServiceImpl(userRepository, gamificationServiceClient, attemptRepository, sourceDestinationMapper);
    }

    @Test
    void verifyCorrectAttempt() {

        //given
        Uzer existingUzer = new Uzer(1L, "Henkie");
        AttemptDTO attemptDTO = new AttemptDTO(12, 12, "Henkie", 144);
        AttemptChecked attemptChecked = new AttemptChecked(1L, existingUzer, 12, 12, 144, true);
        AttemptDtoChecked attemptDtoChecked = new AttemptDtoChecked(1L, 12, 12, 1L, "Henkie", true, 144);
        given(attemptRepository.save(any())).willReturn(attemptChecked);
        given(sourceDestinationMapper.attemptDtoToAttemptChecked(any())).willReturn(attemptChecked);
        given(sourceDestinationMapper.attempCheckedToAttemptDtoChecked(any())).willReturn(attemptDtoChecked);
        //when
        AttemptChecked result = classUnderTest.verifyAttempt(attemptDTO);

        //then
        then(result.isCorrect()).isTrue();
        verify(userRepository).save(new Uzer("Henkie"));
        verify(attemptRepository).save(any());
        verify(gamificationServiceClient).sendAttempt(attemptDtoChecked);
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
        verify(gamificationServiceClient).sendAttempt(any());
    }

    @Test
    void getLatestTenAttempts() {
        //given
        Uzer uzer = new Uzer(1L, "Henkie");
        AttemptChecked attemptChecked1 = new AttemptChecked(1L, null, 12, 12, 12, false);
        AttemptChecked attemptChecked2 = new AttemptChecked(1L, null, 12, 12, 144, true);
        List<AttemptChecked> listAttemptChecked = List.of(attemptChecked1, attemptChecked2);

        AttemptDtoChecked attemptDtoChecked1 = new AttemptDtoChecked(1L, 12, 12, 1L, "Henkie", false, 12);
        AttemptDtoChecked attemptDtoChecked2 = new AttemptDtoChecked(1L, 12, 12, 2L, "Kees", true, 144);
        List<AttemptDtoChecked> listAttemptDtoChecked = List.of(attemptDtoChecked1, attemptDtoChecked2);
        given(attemptRepository.findTop10ByUzerAliasOrderByIdDesc("Henkie")).willReturn(listAttemptChecked);
        given(sourceDestinationMapper.attemptsCheckedToAttemptsCheckedDto(any())).willReturn(listAttemptDtoChecked);
        //when
        List<AttemptDtoChecked> result = classUnderTest.getUserStats("Henkie");

        //then
        then(result).isEqualTo(listAttemptDtoChecked);
    }
}