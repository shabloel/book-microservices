package com.games.gamification.gamification.services.badges;

import com.games.gamification.gamification.domain.model.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GoldBadgeProcessorTest {

    private GoldBadgeProcessor underTest;
    @BeforeEach
    void setUp() {
        underTest = new GoldBadgeProcessor();
    }

    @Test
    void should_return_gold_badge_when_score_above_400() {
        Optional<BadgeType> result = underTest.processForOptionalBadge(401, List.of(), null);
        assertThat(result).contains(BadgeType.GOLD);
    }
    @Test
    void should_return_no_badge_when_score_under_400() {
        Optional<BadgeType> result = underTest.processForOptionalBadge(399, List.of(), null);
        assertThat(result).isEmpty();
    }
}