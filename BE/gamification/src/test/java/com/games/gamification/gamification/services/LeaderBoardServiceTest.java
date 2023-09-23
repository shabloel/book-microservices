package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.model.BadgeCard;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.LeaderBoardRow;
import com.games.gamification.gamification.repositories.BadgeRepo;
import com.games.gamification.gamification.repositories.ScoreCardRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LeaderBoardServiceTest {

    @Mock
    ScoreCardRepo scoreCardRepo;

    @Mock
    BadgeRepo badgeRepo;
    private LeaderBoardService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new LeaderBoardServiceImpl(scoreCardRepo, badgeRepo);
    }

    @Test
    void should_get_current_leaderboard() {
        //given
        LeaderBoardRow leaderBoardRow = new LeaderBoardRow(1L, 150L);
        given(scoreCardRepo.findFirstTen()).willReturn(List.of(leaderBoardRow));
        given(badgeRepo.findByUserIdOrderByBadgeTimestampDesc(anyLong()))
                .willReturn(List.of(new BadgeCard(1L, BadgeType.BRONZE)));
        List<LeaderBoardRow>  expectedLeaderBoardRows =
                List.of(new LeaderBoardRow(1L, 150L, List.of(BadgeType.BRONZE.getDescription())));
        //when
        List<LeaderBoardRow> result = classUnderTest.getCurrentLeaderBoard();

        //then
        then(result).isEqualTo(expectedLeaderBoardRows);
    }
}