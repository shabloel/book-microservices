package com.games.multiplication.services;

import com.games.multiplication.domain.dto.AttemptCheckedDto;
import com.games.multiplication.domain.dto.AttemptDTO;
import com.games.multiplication.domain.model.Attempt;
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
        AttemptDTO attemptDTO =
                new AttemptDTO(12, 12, "Henkie", 144);
        Attempt attempt = new Attempt(1L, existingUzer, 12, 12, 144, true);
        given(attemptRepository.save(any())).willReturn(attempt);
        given(sourceDestinationMapper.attemptDtoToAttemptEntity(any())).willReturn(attempt);
        //when
        Attempt result = classUnderTest.verifyAttempt(attemptDTO);

        AttemptCheckedDto attemptCheckedDto = sourceDestinationMapper.attemptToAttemptCheckedDto(attempt);

        //then
        then(result.isCorrect()).isTrue();
        verify(userRepository).save(new Uzer("Henkie"));
        verify(attemptRepository).save(any());
        verify(gamificationServiceClient).sendAttempt(attemptCheckedDto);
    }

    @Test
    void verifyWrongAttempt() {

        //given
        Uzer existingUzer = new Uzer(1L, "Henkie");
        AttemptDTO attemptDTO =
                new AttemptDTO(12, 12, "Henkie", 112);
        Attempt attempt = new Attempt(1L, existingUzer, 12, 12, 144, true);

        given(sourceDestinationMapper.attemptDtoToAttemptEntity(any())).willReturn(attempt);
        given(attemptRepository.save(any())).willReturn(attempt);

        //when
        Attempt result = classUnderTest.verifyAttempt(attemptDTO);

        //then
        then(result.isCorrect()).isFalse();
        verify(userRepository).save(new Uzer("Henkie"));
        verify(attemptRepository).save(any());
    }

    @Test
    void checkExistingUserTest() {
        //given
        Uzer existingUzer = new Uzer(1L, "Henkie");
        Attempt attempt = new Attempt(1L, existingUzer, 12, 12, 144, true);
        given(attemptRepository.save(any())).willReturn(attempt);
        given(sourceDestinationMapper.attemptDtoToAttemptEntity(any())).willReturn(attempt);
        given(userRepository.findByAlias("Henkie")).willReturn(Optional.of(existingUzer));
        AttemptDTO attemptDTO = new AttemptDTO(12, 12, "Henkie", 144);


        //when
        Attempt result = classUnderTest.verifyAttempt(attemptDTO);

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
        Attempt attempt1 = new Attempt(1L, uzer, 12, 12, 144, false);
        Attempt attempt2 = new Attempt(1L, uzer, 12, 12, 144, false);
        List<Attempt> listAttempts = List.of(attempt1, attempt2);
        given(attemptRepository.findTop10ByUzerAliasOrderByIdDesc("Henkie")).willReturn(listAttempts);

        //when
        List<Attempt> result = classUnderTest.getUserStats("Henkie");

        //then
        then(result).isEqualTo(listAttempts);
    }
}