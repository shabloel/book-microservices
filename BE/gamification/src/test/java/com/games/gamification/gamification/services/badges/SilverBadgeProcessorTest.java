package com.games.gamification.gamification.services.badges;

import com.games.gamification.gamification.domain.model.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SilverBadgeProcessorTest {

    private SilverBadgeProcessor underTest;

    @BeforeEach
    void setUp() {
        underTest = new SilverBadgeProcessor();
    }

    @Test
    void should_return_silverbadge_when_score_over_150() {
        Optional<BadgeType> result = underTest.processForOptionalBadge(151, List.of(), null);
        assertThat(result).contains(BadgeType.SILVER);
    }
    @Test
    void should_return_NO_silverbadge_when_score_under_150() {
        Optional<BadgeType> result = underTest.processForOptionalBadge(149, List.of(), null);
        assertThat(result).isEmpty();
    }
}