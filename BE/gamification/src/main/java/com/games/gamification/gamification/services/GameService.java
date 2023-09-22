package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.Attempt;
import com.games.gamification.gamification.domain.model.BadgeType;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface GameService {

    Optional<GameResult> newAttemptFromUser(Attempt challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
