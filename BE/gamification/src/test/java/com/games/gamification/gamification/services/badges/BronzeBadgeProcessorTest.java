package com.games.gamification.gamification.services.badges;

import com.games.gamification.gamification.domain.model.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BronzeBadgeProcessorTest {

    private BronzeBadgeProcessor underTest;
    @BeforeEach
    void setUp() {
        underTest = new BronzeBadgeProcessor();
    }

    @Test
    void should_return_bronze_badgetype_when_score_above_60() {
        Optional<BadgeType> result = underTest.processForOptionalBadge(60, List.of(), null);
        assertThat(result).contains(BadgeType.BRONZE);
    }

    @Test
    void should_return_empty_optional_when_score_below_60() {
        Optional<BadgeType> result = underTest.processForOptionalBadge(49, List.of(), null);
        assertThat(result).isEmpty();
    }
}