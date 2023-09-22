package com.games.gamification.gamification.testhelpers;

import com.games.gamification.gamification.domain.model.BadgeCard;
import com.games.gamification.gamification.domain.model.BadgeType;
import com.games.gamification.gamification.domain.model.ScoreCard;

import java.util.List;

public class CreateEntities {

    public static List<ScoreCard> createListOfScoreCards() {
        List.of(createScoreCard(true));
        return null;
    }

    public static ScoreCard createScoreCard(final boolean hasId) {
        if (hasId) {
            return new ScoreCard(1L, 1L, 1L, System.currentTimeMillis(), 10);
        } else {
            return new ScoreCard(1L, 1L);
        }
    }

    public static List<BadgeCard> createListOfBadgecardsFirstWon() {
        return List.of(createBadgeCardFirstWon(true));
    }

    public static BadgeCard createBadgeCardFirstWon(final boolean hasId) {
        if (hasId) {
            return new BadgeCard(1L, 1L, System.currentTimeMillis(), BadgeType.FIRST_WON);
        } else {
            return new BadgeCard(1L, BadgeType.FIRST_WON);
        }
    }

}
