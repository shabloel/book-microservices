package com.games.gamification.gamification.services;

import com.games.gamification.gamification.domain.dto.AttemptCheckedEvent;
import com.games.gamification.gamification.domain.model.BadgeType;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface GameService {

    Optional<GameResult> newAttemptFromUser(AttemptCheckedEvent challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
