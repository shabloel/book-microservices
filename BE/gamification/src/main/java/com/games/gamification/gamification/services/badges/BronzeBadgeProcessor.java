package com.games.gamification.gamification.services.badges;

import com.games.gamification.gamification.domain.dto.AttemptCheckedEvent;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BronzeBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards, AttemptCheckedEvent challenge) {
        return currentScore > 50 ? Optional.of(BadgeType.BRONZE) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
