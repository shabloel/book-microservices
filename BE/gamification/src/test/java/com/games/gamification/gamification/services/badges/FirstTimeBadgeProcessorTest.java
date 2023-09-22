package com.games.gamification.gamification.services.badges;

import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FirstTimeBadgeProcessorTest {

    private BadgeProcessor underTest;
    @BeforeEach
    public void setUp() {
        underTest = new FirstTimeBadgeProcessor();
    }

    @Test
    public void should_give_first_time_when_one_scorecard() {
        Optional<BadgeType> result = underTest.processForOptionalBadge(10, List.of(new ScoreCard(1L, 1L)), null);
        assertThat(result).contains(BadgeType.FIRST_WON);
    }
    @Test
    public void should_NOT_give_first_time_when_one_scorecard() {
        Optional<BadgeType> result = underTest.processForOptionalBadge(10, List.of(new ScoreCard(1L, 1L), new ScoreCard(1L, 2L)), null);
        assertThat(result).isEmpty();
    }
}