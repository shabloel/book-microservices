package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.ChallengeSolvedDto;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {

    Optional<BadgeType> processForOptionalBadge(int currentScore,
                                                List<ScoreCard> scoreCards,
                                                ChallengeSolvedDto challenge);

    BadgeType badgeType();
}
