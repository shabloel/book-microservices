package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.Attempt;
import com.games.gamification.gamification.domain.model.BadgeCard;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    private GameService classUnderTest;

    @Mock
    private ScoreCardRepo scoreCardRepo;

    @Mock
    private BadgeRepo badgeRepo;

    @Mock
    private BadgeProcessor badgeProcessor;

    @BeforeEach
    void setUp() {
        classUnderTest = new GameServiceImpl(badgeRepo, scoreCardRepo, List.of(badgeProcessor));
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
        Long userId = 1L, attemptId = 1L;
        String userAlias = "Henk";
        var attempt = new Attempt(
                attemptId, true, 20, 70, userId, userAlias);
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);

        //when
        when(scoreCardRepo.getTotalScoreForUser(userId)).thenReturn(Optional.of(10));
        when(scoreCardRepo.findByUserIdOrderByScoreTimestampDesc(userId)).thenReturn(List.of(scoreCard));
        when(badgeRepo.findByUserIdOrderByBadgeTimestampDesc(userId)).thenReturn(List.of(new BadgeCard(userId, BadgeType.FIRST_WON)));
        when(badgeProcessor.badgeType()).thenReturn(BadgeType.LUCKY_NUMBER);
        when(badgeProcessor.processForOptionalBadge(10, List.of(scoreCard), attempt)).thenReturn(Optional.of(BadgeType.LUCKY_NUMBER));

        final Optional<GameService.GameResult> result = classUnderTest.newAttemptFromUser(attempt);

        //then
        then(result).isEqualTo(Optional.of(new GameService.GameResult(10, List.of(BadgeType.LUCKY_NUMBER))));
        verify(scoreCardRepo).save(scoreCard);
        verify(badgeRepo).saveAll(List.of(new BadgeCard(userId, BadgeType.LUCKY_NUMBER)));
    }

    @Test
    void newAttemptFromUser_InCorrect_Attempt() {
        //given
        Long userId = 1L, attemptId = 1L;
        String userAlias = "Henk";
        var attempt = new Attempt(
                attemptId, false, 20, 70, userId, userAlias);
        Optional<GameService.GameResult> result = classUnderTest.newAttemptFromUser(attempt);
        then(result).isEqualTo(Optional.of(new GameService.GameResult(0, List.of())));
    }
}