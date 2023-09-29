package com.games.gamification.gamification.services.badges;

import com.games.gamification.gamification.domain.dto.AttemptDtoChecked;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {

    Optional<BadgeType> processForOptionalBadge(int currentScore,
                                                List<ScoreCard> scoreCards,
                                                AttemptDtoChecked challenge);

    BadgeType badgeType();
}
