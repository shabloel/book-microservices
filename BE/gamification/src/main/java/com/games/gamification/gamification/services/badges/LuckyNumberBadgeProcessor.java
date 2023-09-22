package com.games.gamification.gamification.services.badges;

import com.games.gamification.gamification.domain.dto.Attempt;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;
import com.games.gamification.gamification.services.badges.BadgeProcessor;

import java.util.List;
import java.util.Optional;

public class LuckyNumberBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore,
                                                       List<ScoreCard> scoreCards,
                                                       Attempt challenge) {
        return (challenge.getFactorA() == 42 || challenge.getFactorB() == 42) ?
                Optional.of(BadgeType.LUCKY_NUMBER) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.LUCKY_NUMBER;
    }
}
