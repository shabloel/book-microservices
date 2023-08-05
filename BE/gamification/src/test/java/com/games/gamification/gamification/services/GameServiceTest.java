package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.ChallengeSolvedDto;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.repositories.BadgeRepo;
import com.games.gamification.gamification.repositories.ScoreCardRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    GameService classUnderTest;

    @Mock
    ScoreCardRepo scoreCardRepo;

    @Mock
    BadgeRepo badgeRepo;

    @Mock
    List<BadgeProcessor> badgeProcessors;

    @BeforeEach
    void setUp() {
        classUnderTest = new GameServiceImpl(badgeRepo, scoreCardRepo, badgeProcessors);
    }
    @Test
    void newAttemptFromUser_Correct_Attempt() {
        //given
        List<BadgeType> badgeTypes = List.of(BadgeType.FIRST_WON);
        ChallengeSolvedDto challengeSolvedDto = new ChallengeSolvedDto(
                1, true, 12, 12, 1, "Henk");

        //when
        Optional<GameService.GameResult> result = classUnderTest.newAttemptFromUser(challengeSolvedDto);

        //then
        then(result).isEqualTo(new GameService.GameResult(10, badgeTypes ));
    }


}