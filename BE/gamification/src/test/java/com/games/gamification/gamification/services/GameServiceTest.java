package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.ChallengeSolvedDto;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.repositories.BadgeRepo;
import com.games.gamification.gamification.repositories.ScoreCardRepo;
import com.games.gamification.gamification.testhelpers.CreateEntities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

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
    void newAttemptFromUser_empty_challenge() {

        //when
        Optional<GameService.GameResult> result = classUnderTest.newAttemptFromUser(null);

        //then
        then(result).isEqualTo(Optional.empty());
    }
    @Test
    void newAttemptFromUser_Correct_Attempt() {
        //given
        List<BadgeType> badgeTypes = List.of(BadgeType.FIRST_WON);
        ChallengeSolvedDto challengeSolvedDto = new ChallengeSolvedDto(
                1, true, 12, 12, 1, "Henk");

        //when
        when(scoreCardRepo.save(any())).thenReturn(Collections.emptyList());
        when(scoreCardRepo.getTotalScoreForUser(anyLong())).thenReturn(Optional.of(50));
        when(scoreCardRepo.findByUserIdOrderByScoreTimestampDesc(anyLong())).thenReturn(CreateEntities.createListOfScoreCards(1));
        when(badgeRepo.findByUserIdOrderByBadgeTimestampBadgeTimestampDesc(anyLong())).thenReturn(CreateEntities.createListOfBadgecards(1));
        Optional<GameService.GameResult> result = classUnderTest.newAttemptFromUser(challengeSolvedDto);

        //then
        then(result).isEqualTo(new GameService.GameResult(10, badgeTypes ));
    }


}