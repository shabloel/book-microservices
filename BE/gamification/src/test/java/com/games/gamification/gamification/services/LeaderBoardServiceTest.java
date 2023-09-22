package com.games.gamification.gamification.services;

import com.games.gamification.gamification.repositories.BadgeRepo;
import com.games.gamification.gamification.repositories.ScoreCardRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void getCurrentLeaderBoard() {
        //given

        //when


        //then
    }
}