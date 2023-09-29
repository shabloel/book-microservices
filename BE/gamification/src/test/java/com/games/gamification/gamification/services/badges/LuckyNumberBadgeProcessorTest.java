package com.games.gamification.gamification.services.badges;

import com.games.gamification.gamification.domain.dto.AttemptDtoChecked;
import com.games.gamification.gamification.domain.model.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LuckyNumberBadgeProcessorTest {

    private LuckyNumberBadgeProcessor underTest;

    @BeforeEach
    void setUp() {
        underTest = new LuckyNumberBadgeProcessor();
    }

    @Test
    void should_return_lucky_number_when_factor_42() {
        Optional<BadgeType> result = underTest
                .processForOptionalBadge(10, List.of(), new AttemptDtoChecked(1L, true, 42, 12, 1L, "Henkie"));
        assertThat(result).contains(BadgeType.LUCKY_NUMBER);
    }
    @Test
    void should_not_return_lucky_number_when_factor_not_42() {
        Optional<BadgeType> result = underTest
                .processForOptionalBadge(10, List.of(), new AttemptDtoChecked(1L, true, 43, 12, 1L, "Henkie"));
        assertThat(result).isEmpty();
    }

}